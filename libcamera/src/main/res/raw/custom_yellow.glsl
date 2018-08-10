varying highp vec2 textureCoordinate;
precision highp float;

uniform sampler2D inputImageTexture;


vec3 RGBtoHSL(vec3 c)
{
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
	vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
	return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 HSLtoRGB(vec3 c)
{
	vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
	vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
	return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() 
{

    float c = 0.0;

	lowp vec4 textureColor;
	float xCoordinate = textureCoordinate.x;
	float yCoordinate = textureCoordinate.y;

	highp float redCurveValue;
	highp float greenCurveValue;
	highp float blueCurveValue;

	textureColor = texture2D( inputImageTexture, vec2(xCoordinate, yCoordinate));

	vec3 pColor = vec3(textureColor.r, textureColor.g, textureColor.b);
	vec3 tColor = RGBtoHSL(pColor);
	tColor = clamp(tColor, 0.0, 1.0);

    if (tColor.r >= 0.111 && tColor.r <= 0.139
                    && tColor.g >= 0.2) {
//        c = pColor.b;
//        c = sqrt(c * c * c);
//        c = 0.4;

        pColor.r = 1.0;
        pColor.g = 0.0;
        pColor.b = 0.0;
    } else if (tColor.r >= 69.0/360.0 && tColor.r <= 71.0/360.0
                    && tColor.g >= 0.196 && tColor.g <= 0.55) {
        c = 0.7;
        pColor.b = 1.0;
    } else {
        c = pColor.r * 0.3 + pColor.g * 0.59 + pColor.b * 0.11;
    }

//    gl_FragColor = vec4(c, c, c, 1.0);
    gl_FragColor = vec4(pColor, 1.0);

}
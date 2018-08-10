#extension GL_OES_EGL_image_external : require

precision mediump float;

varying mediump vec2 textureCoordinate;

uniform samplerExternalOES inputImageTexture;
uniform vec2 singleStepOffset;
uniform mediump float params;

const highp vec3 W = vec3(0.299,0.587,0.114);
vec2 blurCoordinates[20];

float hardLight(float color)
{
	if(color <= 0.5)
		color = color * color * 2.0;
	else
		color = 1.0 - ((1.0 - color)*(1.0 - color) * 2.0);
	return color;
}

//
vec3 rgb2hsv(vec3 c)
{
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
	vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 RGBtoHSL(vec3 c) {
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
	vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
	return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 HSLtoRGB(vec3 c) {
	vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
	vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
	return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main(){

    vec3 centralColor = texture2D(inputImageTexture, textureCoordinate).rgb;
    if(params != 0.0){

        blurCoordinates[0] = textureCoordinate.xy + singleStepOffset * vec2(0.0, -10.0);
        blurCoordinates[1] = textureCoordinate.xy + singleStepOffset * vec2(0.0, 10.0);
        blurCoordinates[2] = textureCoordinate.xy + singleStepOffset * vec2(-10.0, 0.0);
        blurCoordinates[3] = textureCoordinate.xy + singleStepOffset * vec2(10.0, 0.0);
        blurCoordinates[4] = textureCoordinate.xy + singleStepOffset * vec2(5.0, -8.0);
        blurCoordinates[5] = textureCoordinate.xy + singleStepOffset * vec2(5.0, 8.0);
        blurCoordinates[6] = textureCoordinate.xy + singleStepOffset * vec2(-5.0, 8.0);
        blurCoordinates[7] = textureCoordinate.xy + singleStepOffset * vec2(-5.0, -8.0);
        blurCoordinates[8] = textureCoordinate.xy + singleStepOffset * vec2(8.0, -5.0);
        blurCoordinates[9] = textureCoordinate.xy + singleStepOffset * vec2(8.0, 5.0);
        blurCoordinates[10] = textureCoordinate.xy + singleStepOffset * vec2(-8.0, 5.0);
        blurCoordinates[11] = textureCoordinate.xy + singleStepOffset * vec2(-8.0, -5.0);
        blurCoordinates[12] = textureCoordinate.xy + singleStepOffset * vec2(0.0, -6.0);
        blurCoordinates[13] = textureCoordinate.xy + singleStepOffset * vec2(0.0, 6.0);
        blurCoordinates[14] = textureCoordinate.xy + singleStepOffset * vec2(6.0, 0.0);
        blurCoordinates[15] = textureCoordinate.xy + singleStepOffset * vec2(-6.0, 0.0);
        blurCoordinates[16] = textureCoordinate.xy + singleStepOffset * vec2(-4.0, -4.0);
        blurCoordinates[17] = textureCoordinate.xy + singleStepOffset * vec2(-4.0, 4.0);
        blurCoordinates[18] = textureCoordinate.xy + singleStepOffset * vec2(4.0, -4.0);
        blurCoordinates[19] = textureCoordinate.xy + singleStepOffset * vec2(4.0, 4.0);

        float sampleColor = centralColor.g * 20.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[0]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[1]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[2]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[3]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[4]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[5]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[6]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[7]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[8]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[9]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[10]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[11]).g;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[12]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[13]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[14]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[15]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[16]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[17]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[18]).g * 2.0;
        sampleColor += texture2D(inputImageTexture, blurCoordinates[19]).g * 2.0;

        sampleColor = sampleColor / 48.0;

        float highPass = centralColor.g - sampleColor + 0.5;

        for(int i = 0; i < 5;i++)
        {
            highPass = hardLight(highPass);
        }
        float luminance = dot(centralColor, W);

        float alpha = pow(luminance, params);

        vec3 smoothColor = centralColor + (centralColor-vec3(highPass))*alpha*0.1;

        gl_FragColor = vec4(mix(smoothColor.rgb, max(smoothColor, centralColor), alpha), 1.0);
    }else{

//        //TODO
//        float c = 0.0;
//        vec3 tColor = RGBtoHSL(centralColor.rgb);
//        tColor = clamp(tColor, 0.0, 1.0);
//        if (tColor.r >= 0.111 && tColor.r <= 0.139
//                        && tColor.g >= 0.2) {
//            c = centralColor.b;
//            c = sqrt(c * c * c);
////            centralColor.r = 1.0;
////            centralColor.g = 0.0;
//            centralColor.b = 0.0;
//        } else if (tColor.r >= 69.0/360.0 && tColor.r <= 71.0/360.0
//                        && tColor.g >= 0.196 && tColor.g <= 0.55) {
//            c = 0.7;
//        } else {
//            c = centralColor.r * 0.3 + centralColor.g * 0.59 + centralColor.b * 0.11;
//        }
//        gl_FragColor = vec4(c, c, c, 1.0);
//        gl_FragColor = vec4(centralColor.rgb, 1.0);


//          // TODO Deng 灰度化
//           float avcolor = 1.0;
//           avcolor = centralColor.b;
//           avcolor =  sqrt(avcolor * avcolor * avcolor);
//
//           centralColor.b = avcolor;
//           centralColor.r = avcolor;
//           centralColor.g = avcolor;
//           gl_FragColor = vec4(centralColor.rgb,1.0);
//           // TODO


        gl_FragColor = vec4(centralColor.rgb,1.0);
    }

}
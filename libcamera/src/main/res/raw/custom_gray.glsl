varying highp vec2 textureCoordinate;

uniform sampler2D inputImageTexture;

void main() 
{
    lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);

    lowp float avcolor = 1.0;
    avcolor = textureColor.b;
    avcolor =  sqrt(avcolor * avcolor * avcolor);

    textureColor.r = avcolor;
    textureColor.g = avcolor;
    textureColor.b = avcolor;

    gl_FragColor = vec4(textureColor.rgb, textureColor.w);

}
#version 330

in vec2 texCoord0;

uniform sampler2D sampler;

void main()
{
    gl_FragColor = texture2D(sampler, texCoord0.xy);
}
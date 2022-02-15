#version 320 es
precision mediump float;

in vec4 v_color;
in vec3 v_texCoord;
out vec4 o_color;

uniform sampler2D sampler;
uniform bool u_enableTexture;

void main()
{
	vec4 color;
    if(u_enableTexture)
    {
        color = texture(sampler,vec2(v_texCoord));
    }
    else
    {
        color = v_color;
    }
    o_color = color;
}

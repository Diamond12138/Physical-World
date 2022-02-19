#version 320 es
precision mediump float;

layout(location=0)in vec3 a_position;
layout(location=1)in vec4 a_color;
layout(location=2)in vec3 a_normal;
layout(location=3)in vec3 a_texCoord;
out vec4 v_color;
out vec3 v_normal;
out vec3 v_texCoord;
out vec3 v_position;

uniform mat4 u_model;
uniform mat4 u_view;
uniform mat4 u_projection;

uniform bool u_enableShadow;
uniform vec3 u_lightdirection;

void main()
{
    if(u_enableShadow)
    {
        vec3 A = vec3(0.0,1.0,0.0);
        vec3 N = vec3(0.0,1.0,0.0);
        vec3 S = a_position - (100.0 * normalize(u_lightdirection));
        vec3 V = vec3(u_model * vec4(a_position,1.0));
        vec3 VL = S + (V - S) * (dot(N,(A - S)) / dot(N,(V - S)));
        gl_Position = u_projection * u_view * vec4(VL,1.0);
    }
    else
    {
        gl_Position = u_projection * u_view * u_model * vec4(a_position,1.0);
    }
    v_color = a_color;
    v_normal = normalize(vec3(u_model * vec4(a_normal,0.0)));
    v_texCoord = a_texCoord;
    v_position = normalize(vec3(u_model * vec4(a_position,0.0)));
}

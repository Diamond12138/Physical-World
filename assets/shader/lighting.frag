#version 320 es
precision mediump float;

in vec3 v_position;
in vec4 v_color;
in vec3 v_normal;
in vec3 v_texCoord;
out vec4 o_color;

uniform vec3 u_lightdirection;
uniform vec3 u_cameraposition;

uniform sampler2D sampler;
uniform bool u_enableTexture;
uniform vec4 u_color;

uniform bool u_enableShadow;

void main()
{
    if(u_enableShadow)
    {
        o_color = vec4(0.1,0.1,0.1,1.0);
    }
    else
    {
        vec3 lightdirection = normalize(u_lightdirection);
        float ambient = 0.5;
        float diffuse = max(dot(v_normal,-lightdirection),0.0);
        vec3 reflectdirection = normalize(reflect(lightdirection,v_normal));
        vec3 viewdirection = normalize(u_cameraposition - v_position);
        float specular = pow(max(dot(viewdirection,reflectdirection),0.0),32.0);
        
        vec4 color = vec4(0.0,0.0,0.0,0.0);
        if(u_enableTexture)
        {
            color = texture(sampler,vec2(v_texCoord));
        }
        else
        {
            color = u_color;
        }
        
        o_color = (ambient + diffuse + specular) * color;
    }
}

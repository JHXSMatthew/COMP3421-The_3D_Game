#version 130 core

in vec2 pass_textureCoords;
in vec3 outNormal;
//light direction!
in vec3 direction[2];
in vec3 toCameraVector;

out vec4 RGB_Color;

uniform sampler2D textureSampler;
uniform vec3 cLight[2];
uniform float reflectivity;
uniform float shineDamper;
uniform float ambient;

void main(void){
    vec3 unitNormal = normalize(outNormal);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for(int i = 0 ; i < 2 ; i ++){
        vec3 unitLightVector = normalize(direction[i]);
        //diffuse
        float nDot = dot(unitNormal,unitLightVector);
        float brightness = max(nDot,0.0);
        vec3 diffuse = brightness * cLight[i];

        //specular
        vec3 specular = vec3(0.0,0.0,0.0);
        vec3 unitDirToView = normalize(toCameraVector);
        vec3 lightDirection = - unitLightVector;
        vec3 H = normalize(unitDirToView + unitLightVector);
        if(reflectivity > 0.0){
            float NdotHV = max(dot(unitNormal, H),0.0);
            specular = pow(NdotHV,shineDamper) * cLight[i] * reflectivity;
        }
        totalDiffuse = totalDiffuse + diffuse;
        totalSpecular = totalSpecular + specular;
     }

    RGB_Color = vec4(totalDiffuse,1.0) * texture(textureSampler,pass_textureCoords) + vec4(totalSpecular,1.0) + ambient;
}
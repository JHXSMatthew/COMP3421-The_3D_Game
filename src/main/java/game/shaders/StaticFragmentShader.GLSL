#version 130 core

in vec2 pass_textureCoords;
in vec3 outNormal;
in vec3 direction;
in vec3 toCameraVector;

out vec4 RGB_Color;

uniform sampler2D textureSampler;
uniform vec3 cLight;
uniform float reflectivity;
uniform float shineDamper;

void main(void){
    vec3 unitNormal = normalize(outNormal);
    vec3 unitLightVector = normalize(direction);
    //diffuse
    float nDot = dot(unitNormal,unitLightVector);
    float brightness = max(nDot,0.0);
    vec3 diffuse = brightness * cLight;

    //specular
    vec3 specular = vec3(0.0,0.0,0.0);
    vec3 unitDirToView = normalize(toCameraVector);
    vec3 lightDirection = - unitLightVector;
    vec3 H = normalize(unitDirToView + unitLightVector);
     if(reflectivity > 0.0){
        float NdotHV = max(dot(unitNormal, H),0.0);
        specular = pow(NdotHV,shineDamper) * cLight * reflectivity;
     }

    RGB_Color = vec4(diffuse,1.0) * texture(textureSampler,pass_textureCoords) + vec4(specular,1.0);
}
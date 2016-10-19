#version 130 core

in vec2 pass_textureCoords;
in vec3 outNormal;
in vec3 direction;
in vec3 cameraVector;

out vec4 RGB_Color;

uniform sampler2D textureSampler;
uniform vec3 cLight;
uniform float reflectivity;
uniform float shineDamper;

void main(void){
    //diffuse
    vec3 unitNormal = normalize(outNormal);
    vec3 unitLightVector = normalize(direction);

    float nDot = dot(unitNormal,unitLightVector);
    float brightness = max(nDot,0.0);

    vec3 diffuse = brightness * cLight;

    //specular
    vec3 unitCamera = normalize(cameraVector);
    vec3 lightDirection = - unitLightVector;
    vec3 reflectedLight = reflect(lightDirection);

    float specularFactor = dot(reflectedLight,unitCamera);
    specularFactor = max(specularFactor,0.0);
    float specular = pow(specularFactor,shineDamper) * cLight * reflectivity;


    RGB_Color = vec4(diffuse,1.0) * texture(textureSampler,pass_textureCoords) + vec4(specular,1.0);
}
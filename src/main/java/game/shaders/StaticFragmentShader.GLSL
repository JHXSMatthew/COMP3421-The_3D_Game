#version 130 core

in vec2 pass_textureCoords;
in vec3 outNormal;
in vec3 direction;


out vec4 RGB_Color;

uniform sampler2D textureSampler;
uniform vec3 cLight

void main(void){
    vec unitNormal = normalize(outNormal);
    vec unitLightVector = normalize(direction);

    float nDot = dot(unitLightVector,unitNormal);
    float brightness = max(nDot,0.0);
    vec3 diffuse = brightness * cLight;

    RGB_Color = vec4(diffuse,1.0) * texture(textureSampler,pass_textureCoords);
}
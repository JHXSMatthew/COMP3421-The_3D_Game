#version 130 core

in vec2 pass_textureCoords;

out vec4 RGB_Color;

uniform sampler2D textureSampler;

void main(void){
    RGB_Color = pass_textureCoords;//texture(textureSampler,pass_textureCoords);
}
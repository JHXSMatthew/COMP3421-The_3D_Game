#version 130 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 tMatrix;
uniform mat4 pMatrix;
uniform mat4 vMatrix;

void main(void){
    gl_Position = pMatrix * vMatrix * tMatrix * vec4(position,1.0);
    pass_textureCoords = textureCoords;
}
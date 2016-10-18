#version 130 core


in vec3 color;

out vec4 RGB_Color;


void main(void){
    RGB_Color = vec4(color,1.0);

}
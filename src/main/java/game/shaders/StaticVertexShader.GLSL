#version 130 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 outNormal;
out vec3 direction[2];
out vec3 toCameraVector;

//position/p_camera/view
uniform mat4 tMatrix;
uniform mat4 pMatrix;
uniform mat4 vMatrix;
//light calculations
uniform vec3 dLight[2];
uniform vec3 pCamera;

void main(void){
    vec4 globalPosition = tMatrix * vec4(position,1.0);
    gl_Position = pMatrix * vMatrix * globalPosition;
    pass_textureCoords = textureCoords;

    toCameraVector =  pCamera - globalPosition.xyz;

    //light
    outNormal = (globalPosition * vec4(normal,0.0)).xyz;
    direction = dLight;



}
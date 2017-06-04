#version 120

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

struct BaseLight {
	vec3 colour;
	float intensity;
};

struct DirectionalLight {
	BaseLight base;
	vec3 direction;
};

uniform float specularIntensity;
uniform float specularPower;

uniform vec3 eyePos;
uniform sampler2D diffuse;

uniform DirectionalLight directionalLight;


vec4 calcLight (BaseLight base, vec3 direction, vec3 normal) {
	float diffuseFactor = dot(normal, -direction);
	vec4 diffuseColour = vec4(0,0,0,0);
	vec4 specularColour= vec4(0,0,0,0);

	if(diffuseFactor > 0) {
		diffuseColour = vec4(base.colour, 1.0) * base.intensity * diffuseFactor;

		vec3 directionToEye = normalize(eyePos - worldPos0);
		vec3 reflectDirection = normalize(reflect(direction, normal));

		float specularFactor = dot(directionToEye, reflectDirection);
		specularFactor = pow(specularFactor, specularPower);

		if(specularFactor > 0) {
			specularColour = vec4(base.colour, 1.0) * specularIntensity * specularFactor;
		}
	}

	return diffuseColour + specularColour;
}

vec4 calcDirectionalLight (DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.base, -directionalLight.direction, normal);
}


void main() {
	gl_FragColor = texture2D(diffuse, texCoord0.xy) * calcDirectionalLight(directionalLight, normalize(normal0));
}
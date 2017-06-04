#version 120

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

struct BaseLight {
	vec3 colour;
	float intensity;
};

struct Attenuation {
	float constant;
	float linear;
	float exponent;
};

struct PointLight {
	BaseLight base;
	Attenuation atten;
	vec3 position;
	float range;
};

struct SpotLight {
	PointLight pointLight;
	vec3 direction;
	float cutoff;
};

uniform float specularIntensity;
uniform float specularPower;

uniform vec3 eyePos;
uniform sampler2D diffuse;

uniform SpotLight spotLight;

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

vec4 calcPointLight(PointLight pointLight, vec3 normal) {
	vec3 lightDirection = worldPos0 - pointLight.position;
	float distanceToPoint = length(lightDirection);

	if(distanceToPoint > pointLight.range) {
		return vec4(0,0,0,0);
	}

	lightDirection = normalize(lightDirection);

	vec4 colour = calcLight(pointLight.base, lightDirection, normal);

	float attenuation = pointLight.atten.constant + 
						pointLight.atten.linear * distanceToPoint +
						pointLight.atten.exponent * distanceToPoint * distanceToPoint +
						0.0001;

	return (colour / attenuation);
}


vec4 calcSpotLight(SpotLight spotLight, vec3 normal) {
	vec3 lightDirection = normalize(worldPos0 - spotLight.pointLight.position);
	float spotFactor = dot(lightDirection, spotLight.direction);

	vec4 colour = vec4(0,0,0,0);

	if(spotFactor > spotLight.cutoff) {
		colour = calcPointLight(spotLight.pointLight, normal) * 
				(1.0 - ((1.0 - spotFactor)/(1.0 - spotLight.cutoff))); //TODO: MORE EFFICIENT???
	}

	return colour;
}

void main() {
	gl_FragColor = texture2D(diffuse, texCoord0.xy) * calcSpotLight(spotLight, normalize(normal0));
}
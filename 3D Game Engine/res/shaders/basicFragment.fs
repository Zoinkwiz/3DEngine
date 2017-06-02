#version 330

in vec2 texCoord0;
out vec4 fragColour;

uniform vec3 colour;
uniform sampler2D sampler;

void main() {
	vec4 textureColour = texture2D(sampler, texCoord0.xy);

	if(textureColour == 0) {
		fragColour = vec4(colour, 1);
	} else {
		fragColour = texture(sampler, texCoord0.xy) * vec4(colour,0);
	}
}
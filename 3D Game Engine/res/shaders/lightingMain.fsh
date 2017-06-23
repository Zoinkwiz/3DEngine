#include "sampling.glh"

void main() {
	vec3 directionToEye = normalize(C_eyePos - worldPos0);
	vec2 texCoords = calcParallaxTexCoords(dispMap, tbnMatrix, directionToEye, texCoord0, dispMapScale, dispMapBias);
	
	vec3 normal = normalize(tbnMatrix * (255.0/128.0 * texture2D(normalMap, texCoords).xyz - 1)); //Can't represent up with 2 *..., so 255/128 allows it

  gl_FragColor = texture2D(diffuse, texCoords) * calcLightingEffect(normal, worldPos0);
}
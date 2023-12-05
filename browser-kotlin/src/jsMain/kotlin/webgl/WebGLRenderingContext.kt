// Automatically generated - do not modify!

package webgl

import web.rendering.OffscreenRenderingContext
import web.rendering.RenderingContext

sealed external class WebGLRenderingContext :
    WebGLRenderingContextBase,
    WebGLRenderingContextOverloads,
    OffscreenRenderingContext,
    RenderingContext {

    companion object {
        val DEPTH_BUFFER_BIT: GLenum
        val STENCIL_BUFFER_BIT: GLenum
        val COLOR_BUFFER_BIT: GLenum
        val POINTS: GLenum
        val LINES: GLenum
        val LINE_LOOP: GLenum
        val LINE_STRIP: GLenum
        val TRIANGLES: GLenum
        val TRIANGLE_STRIP: GLenum
        val TRIANGLE_FAN: GLenum
        val ZERO: GLenum
        val ONE: GLenum
        val SRC_COLOR: GLenum
        val ONE_MINUS_SRC_COLOR: GLenum
        val SRC_ALPHA: GLenum
        val ONE_MINUS_SRC_ALPHA: GLenum
        val DST_ALPHA: GLenum
        val ONE_MINUS_DST_ALPHA: GLenum
        val DST_COLOR: GLenum
        val ONE_MINUS_DST_COLOR: GLenum
        val SRC_ALPHA_SATURATE: GLenum
        val FUNC_ADD: GLenum
        val BLEND_EQUATION: GLenum
        val BLEND_EQUATION_RGB: GLenum
        val BLEND_EQUATION_ALPHA: GLenum
        val FUNC_SUBTRACT: GLenum
        val FUNC_REVERSE_SUBTRACT: GLenum
        val BLEND_DST_RGB: GLenum
        val BLEND_SRC_RGB: GLenum
        val BLEND_DST_ALPHA: GLenum
        val BLEND_SRC_ALPHA: GLenum
        val CONSTANT_COLOR: GLenum
        val ONE_MINUS_CONSTANT_COLOR: GLenum
        val CONSTANT_ALPHA: GLenum
        val ONE_MINUS_CONSTANT_ALPHA: GLenum
        val BLEND_COLOR: GLenum
        val ARRAY_BUFFER: GLenum
        val ELEMENT_ARRAY_BUFFER: GLenum
        val ARRAY_BUFFER_BINDING: GLenum
        val ELEMENT_ARRAY_BUFFER_BINDING: GLenum
        val STREAM_DRAW: GLenum
        val STATIC_DRAW: GLenum
        val DYNAMIC_DRAW: GLenum
        val BUFFER_SIZE: GLenum
        val BUFFER_USAGE: GLenum
        val CURRENT_VERTEX_ATTRIB: GLenum
        val FRONT: GLenum
        val BACK: GLenum
        val FRONT_AND_BACK: GLenum
        val CULL_FACE: GLenum
        val BLEND: GLenum
        val DITHER: GLenum
        val STENCIL_TEST: GLenum
        val DEPTH_TEST: GLenum
        val SCISSOR_TEST: GLenum
        val POLYGON_OFFSET_FILL: GLenum
        val SAMPLE_ALPHA_TO_COVERAGE: GLenum
        val SAMPLE_COVERAGE: GLenum
        val NO_ERROR: GLenum
        val INVALID_ENUM: GLenum
        val INVALID_VALUE: GLenum
        val INVALID_OPERATION: GLenum
        val OUT_OF_MEMORY: GLenum
        val CW: GLenum
        val CCW: GLenum
        val LINE_WIDTH: GLenum
        val ALIASED_POINT_SIZE_RANGE: GLenum
        val ALIASED_LINE_WIDTH_RANGE: GLenum
        val CULL_FACE_MODE: GLenum
        val FRONT_FACE: GLenum
        val DEPTH_RANGE: GLenum
        val DEPTH_WRITEMASK: GLenum
        val DEPTH_CLEAR_VALUE: GLenum
        val DEPTH_FUNC: GLenum
        val STENCIL_CLEAR_VALUE: GLenum
        val STENCIL_FUNC: GLenum
        val STENCIL_FAIL: GLenum
        val STENCIL_PASS_DEPTH_FAIL: GLenum
        val STENCIL_PASS_DEPTH_PASS: GLenum
        val STENCIL_REF: GLenum
        val STENCIL_VALUE_MASK: GLenum
        val STENCIL_WRITEMASK: GLenum
        val STENCIL_BACK_FUNC: GLenum
        val STENCIL_BACK_FAIL: GLenum
        val STENCIL_BACK_PASS_DEPTH_FAIL: GLenum
        val STENCIL_BACK_PASS_DEPTH_PASS: GLenum
        val STENCIL_BACK_REF: GLenum
        val STENCIL_BACK_VALUE_MASK: GLenum
        val STENCIL_BACK_WRITEMASK: GLenum
        val VIEWPORT: GLenum
        val SCISSOR_BOX: GLenum
        val COLOR_CLEAR_VALUE: GLenum
        val COLOR_WRITEMASK: GLenum
        val UNPACK_ALIGNMENT: GLenum
        val PACK_ALIGNMENT: GLenum
        val MAX_TEXTURE_SIZE: GLenum
        val MAX_VIEWPORT_DIMS: GLenum
        val SUBPIXEL_BITS: GLenum
        val RED_BITS: GLenum
        val GREEN_BITS: GLenum
        val BLUE_BITS: GLenum
        val ALPHA_BITS: GLenum
        val DEPTH_BITS: GLenum
        val STENCIL_BITS: GLenum
        val POLYGON_OFFSET_UNITS: GLenum
        val POLYGON_OFFSET_FACTOR: GLenum
        val TEXTURE_BINDING_2D: GLenum
        val SAMPLE_BUFFERS: GLenum
        val SAMPLES: GLenum
        val SAMPLE_COVERAGE_VALUE: GLenum
        val SAMPLE_COVERAGE_INVERT: GLenum
        val COMPRESSED_TEXTURE_FORMATS: GLenum
        val DONT_CARE: GLenum
        val FASTEST: GLenum
        val NICEST: GLenum
        val GENERATE_MIPMAP_HINT: GLenum
        val BYTE: GLenum
        val UNSIGNED_BYTE: GLenum
        val SHORT: GLenum
        val UNSIGNED_SHORT: GLenum
        val INT: GLenum
        val UNSIGNED_INT: GLenum
        val FLOAT: GLenum
        val DEPTH_COMPONENT: GLenum
        val ALPHA: GLenum
        val RGB: GLenum
        val RGBA: GLenum
        val LUMINANCE: GLenum
        val LUMINANCE_ALPHA: GLenum
        val UNSIGNED_SHORT_4_4_4_4: GLenum
        val UNSIGNED_SHORT_5_5_5_1: GLenum
        val UNSIGNED_SHORT_5_6_5: GLenum
        val FRAGMENT_SHADER: GLenum
        val VERTEX_SHADER: GLenum
        val MAX_VERTEX_ATTRIBS: GLenum
        val MAX_VERTEX_UNIFORM_VECTORS: GLenum
        val MAX_VARYING_VECTORS: GLenum
        val MAX_COMBINED_TEXTURE_IMAGE_UNITS: GLenum
        val MAX_VERTEX_TEXTURE_IMAGE_UNITS: GLenum
        val MAX_TEXTURE_IMAGE_UNITS: GLenum
        val MAX_FRAGMENT_UNIFORM_VECTORS: GLenum
        val SHADER_TYPE: GLenum
        val DELETE_STATUS: GLenum
        val LINK_STATUS: GLenum
        val VALIDATE_STATUS: GLenum
        val ATTACHED_SHADERS: GLenum
        val ACTIVE_UNIFORMS: GLenum
        val ACTIVE_ATTRIBUTES: GLenum
        val SHADING_LANGUAGE_VERSION: GLenum
        val CURRENT_PROGRAM: GLenum
        val NEVER: GLenum
        val LESS: GLenum
        val EQUAL: GLenum
        val LEQUAL: GLenum
        val GREATER: GLenum
        val NOTEQUAL: GLenum
        val GEQUAL: GLenum
        val ALWAYS: GLenum
        val KEEP: GLenum
        val REPLACE: GLenum
        val INCR: GLenum
        val DECR: GLenum
        val INVERT: GLenum
        val INCR_WRAP: GLenum
        val DECR_WRAP: GLenum
        val VENDOR: GLenum
        val RENDERER: GLenum
        val VERSION: GLenum
        val NEAREST: GLenum
        val LINEAR: GLenum
        val NEAREST_MIPMAP_NEAREST: GLenum
        val LINEAR_MIPMAP_NEAREST: GLenum
        val NEAREST_MIPMAP_LINEAR: GLenum
        val LINEAR_MIPMAP_LINEAR: GLenum
        val TEXTURE_MAG_FILTER: GLenum
        val TEXTURE_MIN_FILTER: GLenum
        val TEXTURE_WRAP_S: GLenum
        val TEXTURE_WRAP_T: GLenum
        val TEXTURE_2D: GLenum
        val TEXTURE: GLenum
        val TEXTURE_CUBE_MAP: GLenum
        val TEXTURE_BINDING_CUBE_MAP: GLenum
        val TEXTURE_CUBE_MAP_POSITIVE_X: GLenum
        val TEXTURE_CUBE_MAP_NEGATIVE_X: GLenum
        val TEXTURE_CUBE_MAP_POSITIVE_Y: GLenum
        val TEXTURE_CUBE_MAP_NEGATIVE_Y: GLenum
        val TEXTURE_CUBE_MAP_POSITIVE_Z: GLenum
        val TEXTURE_CUBE_MAP_NEGATIVE_Z: GLenum
        val MAX_CUBE_MAP_TEXTURE_SIZE: GLenum
        val TEXTURE0: GLenum
        val TEXTURE1: GLenum
        val TEXTURE2: GLenum
        val TEXTURE3: GLenum
        val TEXTURE4: GLenum
        val TEXTURE5: GLenum
        val TEXTURE6: GLenum
        val TEXTURE7: GLenum
        val TEXTURE8: GLenum
        val TEXTURE9: GLenum
        val TEXTURE10: GLenum
        val TEXTURE11: GLenum
        val TEXTURE12: GLenum
        val TEXTURE13: GLenum
        val TEXTURE14: GLenum
        val TEXTURE15: GLenum
        val TEXTURE16: GLenum
        val TEXTURE17: GLenum
        val TEXTURE18: GLenum
        val TEXTURE19: GLenum
        val TEXTURE20: GLenum
        val TEXTURE21: GLenum
        val TEXTURE22: GLenum
        val TEXTURE23: GLenum
        val TEXTURE24: GLenum
        val TEXTURE25: GLenum
        val TEXTURE26: GLenum
        val TEXTURE27: GLenum
        val TEXTURE28: GLenum
        val TEXTURE29: GLenum
        val TEXTURE30: GLenum
        val TEXTURE31: GLenum
        val ACTIVE_TEXTURE: GLenum
        val REPEAT: GLenum
        val CLAMP_TO_EDGE: GLenum
        val MIRRORED_REPEAT: GLenum
        val FLOAT_VEC2: GLenum
        val FLOAT_VEC3: GLenum
        val FLOAT_VEC4: GLenum
        val INT_VEC2: GLenum
        val INT_VEC3: GLenum
        val INT_VEC4: GLenum
        val BOOL: GLenum
        val BOOL_VEC2: GLenum
        val BOOL_VEC3: GLenum
        val BOOL_VEC4: GLenum
        val FLOAT_MAT2: GLenum
        val FLOAT_MAT3: GLenum
        val FLOAT_MAT4: GLenum
        val SAMPLER_2D: GLenum
        val SAMPLER_CUBE: GLenum
        val VERTEX_ATTRIB_ARRAY_ENABLED: GLenum
        val VERTEX_ATTRIB_ARRAY_SIZE: GLenum
        val VERTEX_ATTRIB_ARRAY_STRIDE: GLenum
        val VERTEX_ATTRIB_ARRAY_TYPE: GLenum
        val VERTEX_ATTRIB_ARRAY_NORMALIZED: GLenum
        val VERTEX_ATTRIB_ARRAY_POINTER: GLenum
        val VERTEX_ATTRIB_ARRAY_BUFFER_BINDING: GLenum
        val IMPLEMENTATION_COLOR_READ_TYPE: GLenum
        val IMPLEMENTATION_COLOR_READ_FORMAT: GLenum
        val COMPILE_STATUS: GLenum
        val LOW_FLOAT: GLenum
        val MEDIUM_FLOAT: GLenum
        val HIGH_FLOAT: GLenum
        val LOW_INT: GLenum
        val MEDIUM_INT: GLenum
        val HIGH_INT: GLenum
        val FRAMEBUFFER: GLenum
        val RENDERBUFFER: GLenum
        val RGBA4: GLenum
        val RGB5_A1: GLenum
        val RGB565: GLenum
        val DEPTH_COMPONENT16: GLenum
        val STENCIL_INDEX8: GLenum
        val DEPTH_STENCIL: GLenum
        val RENDERBUFFER_WIDTH: GLenum
        val RENDERBUFFER_HEIGHT: GLenum
        val RENDERBUFFER_INTERNAL_FORMAT: GLenum
        val RENDERBUFFER_RED_SIZE: GLenum
        val RENDERBUFFER_GREEN_SIZE: GLenum
        val RENDERBUFFER_BLUE_SIZE: GLenum
        val RENDERBUFFER_ALPHA_SIZE: GLenum
        val RENDERBUFFER_DEPTH_SIZE: GLenum
        val RENDERBUFFER_STENCIL_SIZE: GLenum
        val FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE: GLenum
        val FRAMEBUFFER_ATTACHMENT_OBJECT_NAME: GLenum
        val FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL: GLenum
        val FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE: GLenum
        val COLOR_ATTACHMENT0: GLenum
        val DEPTH_ATTACHMENT: GLenum
        val STENCIL_ATTACHMENT: GLenum
        val DEPTH_STENCIL_ATTACHMENT: GLenum
        val NONE: GLenum
        val FRAMEBUFFER_COMPLETE: GLenum
        val FRAMEBUFFER_INCOMPLETE_ATTACHMENT: GLenum
        val FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT: GLenum
        val FRAMEBUFFER_INCOMPLETE_DIMENSIONS: GLenum
        val FRAMEBUFFER_UNSUPPORTED: GLenum
        val FRAMEBUFFER_BINDING: GLenum
        val RENDERBUFFER_BINDING: GLenum
        val MAX_RENDERBUFFER_SIZE: GLenum
        val INVALID_FRAMEBUFFER_OPERATION: GLenum
        val UNPACK_FLIP_Y_WEBGL: GLenum
        val UNPACK_PREMULTIPLY_ALPHA_WEBGL: GLenum
        val CONTEXT_LOST_WEBGL: GLenum
        val UNPACK_COLORSPACE_CONVERSION_WEBGL: GLenum
        val BROWSER_DEFAULT_WEBGL: GLenum
    }
}

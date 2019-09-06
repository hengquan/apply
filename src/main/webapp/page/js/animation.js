const WIDTH = $(window).width();
const HEIGHT = $(window).height();

const ROAD_WIDTH = 4.3;
const ROAD_LENGTH = 100;
const GATE_HEIGHT = 1.65;
const REPEAT = 47;

class Exp {

    constructor() {

        this._settings = {
            speed: 0.06,
            bloom: 0.65
        };

        this._bind();
        this._loadTextures();
        this._setupRenderer();
        this._setupReflection();
        this._setupCamera();

        this._createGates();
        this._createRoad();
        this._createCeiling();
        this._createLeftWall();
        this._createRightWall();

        this._setupPostProcessing();
        this._setupEventListeners();

        requestAnimationFrame(this._render);

    }

    _bind() {

        this._render = this._render.bind(this);
        this._keyDownHandler = this._keyDownHandler.bind(this);
        this._keyUpHandler = this._keyUpHandler.bind(this);

    }

    _setupEventListeners() {

        document.body.addEventListener('keydown', this._keyDownHandler);
        document.body.addEventListener('keyup', this._keyUpHandler);

    }

    _loadTextures() {
        
        this._loader = new THREE.TextureLoader();
        this._loader.crossOrigin = 'anonymous';

        // Road textures
        this._roadTextures = [
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/road-1.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/road-2.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/road-3.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/road-4.png')
        ];

        this._roadTextures[0].wrapT = THREE.RepeatWrapping;
        this._roadTextures[1].wrapT = THREE.RepeatWrapping;
        this._roadTextures[2].wrapT = THREE.RepeatWrapping;
        this._roadTextures[3].wrapT = THREE.RepeatWrapping;
        this._roadTextures[0].minFilter = THREE.LinearFilter;
        this._roadTextures[1].minFilter = THREE.LinearFilter;
        this._roadTextures[2].minFilter = THREE.LinearFilter;
        this._roadTextures[3].minFilter = THREE.LinearFilter;

        // Gate textures
        this._gateTextures = [
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/gate-1.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/gate-2.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/gate-3.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/gate-4.png')
        ];

        // Wall textures
        this._wallTextures = [
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/wall-1.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/wall-2.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/wall-3.png'),
            this._loader.load('https://res.cloudinary.com/dbo3jfkpl/image/upload/v1519579527/wall-4.png')
        ];

        this._wallTextures[0].wrapS = THREE.RepeatWrapping;
        this._wallTextures[1].wrapS = THREE.RepeatWrapping;
        this._wallTextures[2].wrapS = THREE.RepeatWrapping;
        this._wallTextures[3].wrapS = THREE.RepeatWrapping;
        this._wallTextures[0].minFilter = THREE.LinearFilter;
        this._wallTextures[1].minFilter = THREE.LinearFilter;
        this._wallTextures[2].minFilter = THREE.LinearFilter;
        this._wallTextures[3].minFilter = THREE.LinearFilter;

    }

    _setupRenderer() {

        this._renderer = new THREE.WebGLRenderer({canvas: document.querySelector('.canvas')});
        this._renderer.setSize(WIDTH, HEIGHT);
        this._renderer.autoClear = false;

        this._scene = new THREE.Scene();
        this._sceneGates = new THREE.Scene();

    }

    _setupReflection() {

        this._reflectRoad = new Reflect({renderer: this._renderer, width: WIDTH, height: HEIGHT});
        this._reflectLeftWall = new Reflect({renderer: this._renderer, width: WIDTH, height: HEIGHT});
        this._reflectRightWall = new Reflect({renderer: this._renderer, width: WIDTH, height: HEIGHT});
        this._reflectCeiling = new Reflect({renderer: this._renderer, width: WIDTH, height: HEIGHT});

    }

    _setupCamera() {

        this._camera = new THREE.PerspectiveCamera(75, WIDTH / HEIGHT, 0.1, 1000);
        this._camera.position.x = 1.035;
        this._camera.position.y = 0.5;
        this._camera.position.z = 5;

    }

    _createRoad() {

        const geometry = new THREE.PlaneGeometry(ROAD_WIDTH, ROAD_LENGTH);
        this._roadMaterial = new THREE.ShaderMaterial({
            uniforms: {
                uRoadTexture: {value: this._roadTextures[0]},
                uReflectionTexture: {value: this._reflectRoad.texture},
                uRepeatY: {value: REPEAT}
            },
            vertexShader: document.getElementById('roadVertexShader').innerHTML,
            fragmentShader: document.getElementById('roadFragmentShader').innerHTML
        });

        const mesh = new THREE.Mesh(geometry, this._roadMaterial);
        mesh.position.z = -ROAD_LENGTH / 2 + 5;
        mesh.rotation.x = -Math.PI * 0.5;

        this._scene.add(mesh);

    }
    
    _createGates() {

        const geometry = new THREE.PlaneGeometry(ROAD_WIDTH, GATE_HEIGHT);
        this._gateMaterial = new THREE.ShaderMaterial({
            uniforms: {
                uTexture: {value: this._gateTextures[3]},
                uShowMask: {value: true},
                uMaskDirection: {value: 1},
                uMaskHorizontal: {value: true},
                uStrength: {value: 1},
            },
            transparent: true,
            vertexShader: document.getElementById('gateVertexShader').innerHTML,
            fragmentShader: document.getElementById('gateFragmentShader').innerHTML
        });

        for (let i = 0; i < 40; i++) {
            const mesh = new THREE.Mesh(geometry, this._gateMaterial);
            mesh.position.y = GATE_HEIGHT * 0.5;
            mesh.position.z = 2.86 + -2.13 * i;
            this._sceneGates.add(mesh);
        }

    }

    _createCeiling() {

        const geometry = new THREE.PlaneGeometry(ROAD_WIDTH, ROAD_LENGTH);
        const material = new THREE.ShaderMaterial({
            uniforms: {
                uReflectionTexture: {value: this._reflectCeiling.texture}
            },
            vertexShader: document.getElementById('ceilingVertexShader').innerHTML,
            fragmentShader: document.getElementById('ceilingFragmentShader').innerHTML
        });

        const mesh = new THREE.Mesh(geometry, material);
        mesh.position.y = GATE_HEIGHT;
        mesh.position.z = -ROAD_LENGTH / 2 + 5;
        mesh.rotation.x = Math.PI * 0.5;

        this._scene.add(mesh);

    }

    _createLeftWall() {

        const geometry = new THREE.PlaneGeometry(ROAD_LENGTH, GATE_HEIGHT);
        this._wallLeftMaterial = new THREE.ShaderMaterial({
            uniforms: {
                uWallTexture: {value: this._wallTextures[0]},
                uReflectionTexture: {value: this._reflectLeftWall.texture},
                uRepeatZ: {value: REPEAT}
            },
            vertexShader: document.getElementById('wallVertexShader').innerHTML,
            fragmentShader: document.getElementById('wallFragmentShader').innerHTML
        });

        const mesh = new THREE.Mesh(geometry, this._wallLeftMaterial);
        mesh.position.x = -ROAD_WIDTH * 0.5;
        mesh.position.y = GATE_HEIGHT * 0.5;
        mesh.position.z = -ROAD_LENGTH / 2 + 5;
        mesh.rotation.y = Math.PI * 0.5;

        this._scene.add(mesh);

    }

    _createRightWall() {

        const geometry = new THREE.PlaneGeometry(ROAD_LENGTH, GATE_HEIGHT);
        this._wallRightMaterial = new THREE.ShaderMaterial({
            uniforms: {
                uWallTexture: {value: this._wallTextures[0]},
                uReflectionTexture: {value: this._reflectRightWall.texture},
                uRepeatZ: {value: REPEAT}
            },
            side: THREE.BackSide,
            vertexShader: document.getElementById('wallVertexShader').innerHTML,
            fragmentShader: document.getElementById('wallFragmentShader').innerHTML
        });

        const mesh = new THREE.Mesh(geometry, this._wallRightMaterial);
        mesh.position.x = ROAD_WIDTH / 2;
        mesh.position.y = GATE_HEIGHT * 0.5;
        mesh.position.z = -ROAD_LENGTH / 2 + 5;
        mesh.rotation.y = Math.PI * 0.5;

        this._scene.add(mesh);

    }

    _swapTextures() {

        if (!this._currentTextureIndex) this._currentTextureIndex = 0;
        this._currentTextureIndex = (this._currentTextureIndex + 1) % 4;

        this._roadMaterial.uniforms.uRoadTexture.value = this._roadTextures[this._currentTextureIndex];
        this._gateMaterial.uniforms.uTexture.value = this._gateTextures[this._currentTextureIndex];
        this._wallLeftMaterial.uniforms.uWallTexture.value = this._wallTextures[this._currentTextureIndex];
        this._wallRightMaterial.uniforms.uWallTexture.value = this._wallTextures[this._currentTextureIndex];

    }

    _setupPostProcessing() {

        const renderPass1 = new RenderPass(this._scene, this._camera);
        const renderPass2 = new RenderPass(this._sceneGates, this._camera);
        renderPass2.clear = false;

        this._bloomPass = new BloomPass({strength: this._settings.bloom});
        this._bloomPass.renderToScreen = true;

        const copyPass = new ShaderPass(CopyShader);
        copyPass.renderToScreen = true;

        this._composer = new EffectComposer(this._renderer);
        this._composer.addPass(renderPass1);
        this._composer.addPass(renderPass2);
        this._composer.addPass(this._bloomPass);
        this._composer.addPass(copyPass);

    }

    _switchLane(direction) {

        TweenMax.to(this._camera.position, 0.3, {x: 1.035 * direction});

    }

    _speedUp() {

        if (this._isSpeedingUp) return;
        this._isSpeedingUp = true;

        TweenMax.to(this._settings, 15, {speed: 1, ease: Sine.easeInOut});

    }

    _slowDown() {

        this._isSpeedingUp = false;
        TweenMax.to(this._settings, 3, {speed: 0.05, ease: Sine.easeOut});

    }

    _render() {

        requestAnimationFrame(this._render);

        this._update();

        this._gateMaterial.uniforms.uShowMask.value = true;

        // Road
        this._gateMaterial.uniforms.uMaskDirection.value = 0;
        this._gateMaterial.uniforms.uMaskHorizontal.value = 1;
        this._gateMaterial.uniforms.uStrength.value = 2.8;
        this._reflectRoad.render(this._sceneGates, this._camera, {y: this._camera.position.y * 2});

        // Ceiling
        this._gateMaterial.uniforms.uMaskDirection.value = 1;
        this._gateMaterial.uniforms.uStrength.value = 12;
        this._reflectCeiling.render(this._sceneGates, this._camera, {y: (this._camera.position.y - GATE_HEIGHT) * 2});

        // Left wall
        this._gateMaterial.uniforms.uMaskHorizontal.value = 0;
        this._gateMaterial.uniforms.uMaskDirection.value = 0;
        this._gateMaterial.uniforms.uStrength.value = 38;
        this._reflectLeftWall.render(this._sceneGates, this._camera, {x: (this._camera.position.x + ROAD_WIDTH * 0.5) * 2});

        // Right wall
        this._gateMaterial.uniforms.uMaskDirection.value = 1;
        this._reflectRightWall.render(this._sceneGates, this._camera, {x: (this._camera.position.x - ROAD_WIDTH * 0.5) * 2});

        this._gateMaterial.uniforms.uShowMask.value = false;
        this._bloomPass.copyUniforms.opacity.value = this._settings.bloom;
        this._composer.render();

    }

    _update() {

        this._counter = this._counter || 0;
        this._counter++;

        this._camera.position.z -= this._settings.speed;
        if (this._camera.position.z < -10.9) this._camera.position.z = 4;

        if (this._counter % 20 === 0) {
            this._swapTextures();
        }

    }

    /**
     * Handlers
     */
    _keyDownHandler(e) {

        switch (e.keyCode) {
            case 37:
            case 65:
                this._switchLane(-1);
                break;
            case 38:
            case 87:
                this._speedUp();
                break;
            case 39:
            case 68:
                this._switchLane(1);
                break;
        }

    }

    _keyUpHandler(e) {

        if (e.keyCode === 38 || e.keyCode === 87) {
            this._slowDown();
        }

    }

}

class Reflect {

    constructor(options) {

        this._width = options.width;
        this._height = options.height;
        this._renderer = options.renderer;
        this._target = new THREE.WebGLRenderTarget(this._width, this._height);
        
        this.texture = this._target.texture;

    }

    render(scene, camera, offset) {

        if (offset.x) camera.position.x -= offset.x;
        if (offset.y) camera.position.y -= offset.y;
        camera.rotation.z = -camera.rotation.z;

        this._renderer.render(scene, camera, this._target, true);

        if (offset.x) camera.position.x += offset.x;
        if (offset.y) camera.position.y += offset.y;
        camera.rotation.z = -camera.rotation.z;

    }

}

const ConvolutionShader = {

	defines: {

		"KERNEL_SIZE_FLOAT": "25.0",
		"KERNEL_SIZE_INT": "25"

	},

	uniforms: {

		"tDiffuse":        { value: null },
		"uImageIncrement": { value: new THREE.Vector2( 0.001953125, 0.0 ) },
		"cKernel":         { value: [] }

	},

	vertexShader: [

		"uniform vec2 uImageIncrement;",

		"varying vec2 vUv;",

		"void main() {",

			"vUv = uv - ( ( KERNEL_SIZE_FLOAT - 1.0 ) / 2.0 ) * uImageIncrement;",
			"gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );",

		"}"

	].join( "\n" ),

	fragmentShader: [

		"uniform float cKernel[ KERNEL_SIZE_INT ];",

		"uniform sampler2D tDiffuse;",
		"uniform vec2 uImageIncrement;",

		"varying vec2 vUv;",

		"void main() {",

			"vec2 imageCoord = vUv;",
			"vec4 sum = vec4( 0.0, 0.0, 0.0, 0.0 );",

			"for( int i = 0; i < KERNEL_SIZE_INT; i ++ ) {",

				"sum += texture2D( tDiffuse, imageCoord ) * cKernel[ i ];",
				"imageCoord += uImageIncrement;",

			"}",

			"gl_FragColor = sum;",

		"}"


	].join( "\n" ),

	buildKernel: function ( sigma ) {

		// We lop off the sqrt(2 * pi) * sigma term, since we're going to normalize anyway.

		function gauss( x, sigma ) {

			return Math.exp( - ( x * x ) / ( 2.0 * sigma * sigma ) );

		}

		var i, values, sum, halfWidth, kMaxKernelSize = 25, kernelSize = 2 * Math.ceil( sigma * 3.0 ) + 1;

		if ( kernelSize > kMaxKernelSize ) kernelSize = kMaxKernelSize;
		halfWidth = ( kernelSize - 1 ) * 0.5;

		values = new Array( kernelSize );
		sum = 0.0;
		for ( i = 0; i < kernelSize; ++ i ) {

			values[ i ] = gauss( i - halfWidth, sigma );
			sum += values[ i ];

		}

		for ( i = 0; i < kernelSize; ++ i ) values[ i ] /= sum;

		return values;

	}
};

class BloomPass {

    constructor(options = {}) {

        this.enabled = true;
        this.needsSwap = true;
        this.clear = false;
        this.renderToScreen = false;

        const strength = options.strength === undefined ? 1 : options.strength;
        const kernelSize = options.kernelSize === undefined ? 25 : options.kernelSize;
        const sigma = options.sigma === undefined ? 4 : options.sigma;
        const resolution = options.resolution === undefined ? 256 : options.resolution;
        
        var pars = {minFilter: THREE.LinearFilter, magFilter: THREE.LinearFilter, format: THREE.RGBAFormat};

        this.renderTargetX = new THREE.WebGLRenderTarget(resolution, resolution, pars);
        this.renderTargetX.texture.name = 'BloomPass.x';
        this.renderTargetY = new THREE.WebGLRenderTarget(resolution, resolution, pars);
        this.renderTargetY.texture.name = 'BloomPass.y';
    
        // copy material
    
        var copyShader = CopyShader;
    
        this.copyUniforms = THREE.UniformsUtils.clone(copyShader.uniforms);
    
        this.copyUniforms['opacity'].value = strength;
    
        this.materialCopy = new THREE.ShaderMaterial({
    
            uniforms: this.copyUniforms,
            vertexShader: copyShader.vertexShader,
            fragmentShader: copyShader.fragmentShader,
            blending: THREE.AdditiveBlending,
            transparent: true
    
        });
    
        // convolution material
    
        var convolutionShader = ConvolutionShader;
    
        this.convolutionUniforms = THREE.UniformsUtils.clone(convolutionShader.uniforms);
    
        this.convolutionUniforms['uImageIncrement'].value = BloomPass.blurX;
        this.convolutionUniforms['cKernel'].value = ConvolutionShader.buildKernel(sigma);
    
        this.materialConvolution = new THREE.ShaderMaterial({
    
            uniforms: this.convolutionUniforms,
            vertexShader: convolutionShader.vertexShader,
            fragmentShader: convolutionShader.fragmentShader,
            defines: {
                'KERNEL_SIZE_FLOAT': kernelSize.toFixed(1),
                'KERNEL_SIZE_INT': kernelSize.toFixed(0)
            }
    
        });
    
        this.needsSwap = false;
    
        this.camera = new THREE.OrthographicCamera(- 1, 1, 1, - 1, 0, 1);
        this.scene  = new THREE.Scene();
    
        this.quad = new THREE.Mesh(new THREE.PlaneBufferGeometry(2, 2), null);
        this.quad.frustumCulled = false; // Avoid getting clipped
        this.scene.add(this.quad);
    
    }

    render(renderer, writeBuffer, readBuffer, delta, maskActive) {

        if (maskActive) renderer.context.disable(renderer.context.STENCIL_TEST);

        // Render quad with blured scene into texture (convolution pass 1)

        this.quad.material = this.materialConvolution;

        this.convolutionUniforms['tDiffuse'].value = readBuffer.texture;
        this.convolutionUniforms['uImageIncrement'].value = BloomPass.blurX;

        renderer.render(this.scene, this.camera, this.renderTargetX, true);

        // Render quad with blured scene into texture (convolution pass 2)

        this.convolutionUniforms['tDiffuse'].value = this.renderTargetX.texture;
        this.convolutionUniforms['uImageIncrement'].value = BloomPass.blurY;

        renderer.render(this.scene, this.camera, this.renderTargetY, true);

        // Render original scene with superimposed blur to texture

        this.quad.material = this.materialCopy;

        this.copyUniforms['tDiffuse'].value = this.renderTargetY.texture;

        if (maskActive) renderer.context.enable(renderer.context.STENCIL_TEST);

        renderer.render(this.scene, this.camera, readBuffer, this.clear);

    }

}

BloomPass.blurX = new THREE.Vector2(0.001953125, 0.0);
BloomPass.blurY = new THREE.Vector2(0.0, 0.001953125);

function RenderPass (scene, camera, overrideMaterial, clearColor, clearAlpha) {
    if (!(this instanceof RenderPass)) return new RenderPass(scene, camera, overrideMaterial, clearColor, clearAlpha)

    this.scene = scene
    this.camera = camera

    this.overrideMaterial = overrideMaterial

    this.clearColor = clearColor
    this.clearAlpha = (clearAlpha !== undefined) ? clearAlpha : 1

    this.oldClearColor = new THREE.Color()
    this.oldClearAlpha = 1

    this.enabled = true
    this.clear = true
    this.needsSwap = false
}

RenderPass.prototype.render = function (renderer, writeBuffer, readBuffer, delta) {
    this.scene.overrideMaterial = this.overrideMaterial

    if (this.clearColor) {
        this.oldClearColor.copy(renderer.getClearColor())
        this.oldClearAlpha = renderer.getClearAlpha()

        renderer.setClearColor(this.clearColor, this.clearAlpha)
    }

    renderer.render(this.scene, this.camera, readBuffer, this.clear)

    if (this.clearColor) {
        renderer.setClearColor(this.oldClearColor, this.oldClearAlpha)
    }

    this.scene.overrideMaterial = null
}

const CopyShader = {
    uniforms: {
        'tDiffuse': { type: 't', value: null },
        'opacity': { type: 'f', value: 1.0 }
    },
    vertexShader: [
        'varying vec2 vUv;',

        'void main() {',

        'vUv = uv;',
        'gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );',

        '}'
    ].join('\n'),
    fragmentShader: [
        'uniform float opacity;',

        'uniform sampler2D tDiffuse;',

        'varying vec2 vUv;',

        'void main() {',

        'vec4 texel = texture2D( tDiffuse, vUv );',
        'gl_FragColor = opacity * texel;',

        '}'
    ].join('\n')
}

function ShaderPass (shader, textureID) {
    if (!(this instanceof ShaderPass)) return new ShaderPass(shader, textureID)

    this.textureID = (textureID !== undefined) ? textureID : 'tDiffuse'

    this.uniforms = THREE.UniformsUtils.clone(shader.uniforms)

    this.material = new THREE.ShaderMaterial({
        uniforms: this.uniforms,
        vertexShader: shader.vertexShader,
        fragmentShader: shader.fragmentShader
    })

    this.renderToScreen = false

    this.enabled = true
    this.needsSwap = true
    this.clear = false
}

ShaderPass.prototype.render = function (renderer, writeBuffer, readBuffer, delta) {
    if (this.uniforms[ this.textureID ]) {
        this.uniforms[ this.textureID ].value = readBuffer.texture
    }

    EffectComposer.quad.material = this.material

    if (this.renderToScreen) {
        renderer.render(EffectComposer.scene, EffectComposer.camera)
    } else {
        renderer.render(EffectComposer.scene, EffectComposer.camera, writeBuffer, this.clear)
    }
}

function MaskPass (scene, camera) {
    if (!(this instanceof MaskPass)) return new MaskPass(scene, camera)

    this.scene = scene
    this.camera = camera

    this.enabled = true
    this.clear = true
    this.needsSwap = false

    this.inverse = false
}

MaskPass.prototype.render = function (renderer, writeBuffer, readBuffer, delta) {
    var context = renderer.context

    context.colorMask(false, false, false, false)
    context.depthMask(false)

    var writeValue, clearValue

    if (this.inverse) {
        writeValue = 0
        clearValue = 1
    } else {
        writeValue = 1
        clearValue = 0
    }

    context.enable(context.STENCIL_TEST)
    context.stencilOp(context.REPLACE, context.REPLACE, context.REPLACE)
    context.stencilFunc(context.ALWAYS, writeValue, 0xffffffff)
    context.clearStencil(clearValue)

    renderer.render(this.scene, this.camera, readBuffer, this.clear)
    renderer.render(this.scene, this.camera, writeBuffer, this.clear)

    context.colorMask(true, true, true, true)
    context.depthMask(true)

    context.stencilFunc(context.EQUAL, 1, 0xffffffff) // draw if == 1
    context.stencilOp(context.KEEP, context.KEEP, context.KEEP)
}

function ClearMaskPass (scene, camera) {
    if (!(this instanceof ClearMaskPass)) return new ClearMaskPass(scene, camera)
    this.enabled = true
}

ClearMaskPass.prototype.render = function (renderer, writeBuffer, readBuffer, delta) {
    var context = renderer.context
    context.disable(context.STENCIL_TEST)
}


function EffectComposer (renderer, renderTarget) {
	this.renderer = renderer

	if (renderTarget === undefined) {
		var width = window.innerWidth || 1
		var height = window.innerHeight || 1
		var parameters = { minFilter: THREE.LinearFilter, magFilter: THREE.LinearFilter, format: THREE.RGBFormat, stencilBuffer: false }

		renderTarget = new THREE.WebGLRenderTarget(width, height, parameters)
	}

	this.renderTarget1 = renderTarget
	this.renderTarget2 = renderTarget.clone()

	this.writeBuffer = this.renderTarget1
	this.readBuffer = this.renderTarget2

	this.passes = []

	this.copyPass = new ShaderPass(CopyShader)
}

EffectComposer.prototype.swapBuffers = function () {
	var tmp = this.readBuffer
	this.readBuffer = this.writeBuffer
	this.writeBuffer = tmp
}

EffectComposer.prototype.addPass = function (pass) {
	this.passes.push(pass)
}

EffectComposer.prototype.insertPass = function (pass, index) {
	this.passes.splice(index, 0, pass)
}

EffectComposer.prototype.render = function (delta) {
	this.writeBuffer = this.renderTarget1
	this.readBuffer = this.renderTarget2

	var maskActive = false

	for (var i = 0; i < this.passes.length; i++) {
		let pass = this.passes[ i ]

		if (!pass.enabled) continue

		pass.render(this.renderer, this.writeBuffer, this.readBuffer, delta, maskActive)

		if (pass.needsSwap) {
			if (maskActive) {
				var context = this.renderer.context

				context.stencilFunc(context.NOTEQUAL, 1, 0xffffffff)

				this.copyPass.render(this.renderer, this.writeBuffer, this.readBuffer, delta)

				context.stencilFunc(context.EQUAL, 1, 0xffffffff)
			}

			this.swapBuffers()
		}

		if (pass instanceof MaskPass) {
			maskActive = true
		} else if (pass instanceof ClearMaskPass) {
			maskActive = false
		}
	}
}

EffectComposer.prototype.reset = function (renderTarget) {
	if (renderTarget === undefined) {
		renderTarget = this.renderTarget1.clone()

		renderTarget.width = window.innerWidth
		renderTarget.height = window.innerHeight
	}

	this.renderTarget1 = renderTarget
	this.renderTarget2 = renderTarget.clone()

	this.writeBuffer = this.renderTarget1
	this.readBuffer = this.renderTarget2
}

EffectComposer.prototype.setSize = function (width, height) {
	var renderTarget = this.renderTarget1.clone()

	renderTarget.width = width
	renderTarget.height = height

	this.reset(renderTarget)
}

EffectComposer.camera = new THREE.OrthographicCamera(-1, 1, 1, -1, 0, 1)

EffectComposer.quad = new THREE.Mesh(new THREE.PlaneGeometry(2, 2), null)

EffectComposer.scene = new THREE.Scene()
EffectComposer.scene.add(EffectComposer.quad)


new Exp();
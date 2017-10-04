 var World = {
loaded: false,
rotating: false,
init: function initFn() {
this.createModelAtLocation();
},

createModelAtLocation: function createModelAtLocationFn() {
/*
First a location where the model should be displayed will be defined. This location will be relativ to the user.
*/
var location = new AR.GeoLocation(41.541223,  -8.432854);
var altitude = location.altitude;

/*
Next the model object is loaded.
*/
var modelEarth = new AR.Model("assets/earth.wt3", {
onLoaded: this.worldLoaded,
scale: {
x: 1,
y: 1,
z: 1
}
});

var indicatorImage = new AR.ImageResource("assets/marker_idle.png");
var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
});

/*
Putting it all together the location and 3D model is added to an AR.GeoObject.
*/
var obj = new AR.GeoObject(location, {
drawables: {
cam: [modelEarth],
indicator: [indicatorDrawable]
}
});
},

worldLoaded: function worldLoadedFn() {
World.loaded = true;
var e = document.getElementById('loadingMessage');
e.parentElement.removeChild(e);
}
};
/* Set a custom function where location changes are forwarded to. There is also a possibility to set AR.context.onLocationChanged to null. In this case the function will not be called anymore and no further location updates will be received. */
AR.context.onLocationChanged = World.locationChanged;

AR.logger.activateDebugMode();

World.init();
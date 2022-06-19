sap.ui.define([
    "sap/ui/core/UIComponent",
    "sap/ui/Device",
    "rcAdminApp/model/models"
],
    function (UIComponent, Device, models) {
        "use strict";

        return UIComponent.extend("rcAdminApp.Component", {
            metadata: {
                manifest: "json"
            },
            myData: {
                _isLoading: true,
                _sHash: "",
                navigationNeeded: false
            },
            /**
             * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
             * @public
             * @override
             */
            init: function () {
                // call the base component's init function
                UIComponent.prototype.init.apply(this, arguments);

                // enable routing
                this.getRouter().initialize();

                var oRouter = this.getRouter();
                oRouter.attachBypassed(this._onBypassed, this);

                // set the device model
                this.setModel(models.createDeviceModel(), "device");

                var oModel = this.getModel();
                var oList = oModel.bindList("/Routes");
                var me = this;

                oList.requestContexts().then(function (aContext) {
                    aContext.forEach(function (element) {
                        console.log("add route " + element.getProperty("Id"));

                        let id = element.getProperty("Id");
                        let pattern = element.getProperty("Pattern");
                        let viewName = element.getProperty("View");

                        var oTarget = {
                            viewName: viewName,
                        };

                        oRouter.getTargets().addTarget(id, oTarget);

                        var oRoute = {
                            name: id,
                            pattern: pattern,
                            target: id
                        }

                        oRouter.addRoute(oRoute);
                    });
                    console.log("set loading to false");
                    me.myData._isLoading = false;
                    if (me.myData.navigationNeeded) {
                        var oRoute = oRouter.getRouteByHash(me.myData._sHash);
                        console.log("route " + oRoute);
                        if (oRoute) {
                            console.log("reparse " + me.myData._sHash);
                            oRouter.parse(me.myData._sHash);
                        }
                        me.myData.navigationNeeded = false;
                    }
                });

            },
            _onBypassed: function (oEvent) {
                var sHash = oEvent.getParameter("hash");
                console.log("_onBypassed Hash=" + sHash);

                if (this.myData._isLoading) {
                    console.log(" isloading " + this.myData._isLoading);
                    this.myData._sHash = sHash;
                    this.myData.navigationNeeded = true;
                } else {
                    var oRouter = this.getRouter();
                    var oRoute = oRouter.getRouteByHash(sHash);
                    console.log("route " + oRoute + " isloading " + this._loading);
                    if (oRoute) {
                        console.log("parse " + sHash);
                        oRouter.parse(sHash);
                    } else {
                        oRouter.navTo("notFound");
                    }
                }
            }
        });
    }
);
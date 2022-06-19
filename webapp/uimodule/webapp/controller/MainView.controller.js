sap.ui.define([
    "rcAdminApp/controller/BaseController"

],
    /**
     * @param {typeof sap.ui.core.mvc.Controller} Controller
     */
    function (BaseController) {
        "use strict";

        return BaseController.extend("rcAdminApp.controller.MainView", {
            onInit: function () {

            },
            onBerufsfelder: function () {
                this.getRouter().navTo("berufsfelder", {}, false);
            },
            onPress: function () {
                sap.m.URLHelper.redirect("https://clouduiapp.azurewebsites.net/api/todos",false);
            },
            onPress2: function () {
                this.getRouter().navTo("html", {}, false /*no history*/);
            },
            onAnmeldungen: function () {
                this.getRouter().navTo("anmeldungen", {}, false);
            },
            onVeranstaltungen: function () {
                this.getRouter().navTo("veranstaltungen", {}, false);
            },
            onReferenten: function () {
                this.getRouter().navTo("referenten", {}, false);
            },
            onInfo: function () {
                this.getRouter().navTo("info", {}, false);
            }

        });
    });

SUMMARY = "The AWS IoT client establishes a connection to AWS IoT, executes jobs and sends status information."

LICENSE = "CLOSED"

SRC_URI = "\
    https://github.com/osb-cc-esec/meta-esec-awsclient/releases/download/v${PV}/awsclient_${PV}_aarch64 \
    file://awsclient.service \
    file://awsclient.timer \
"

SRC_URI[md5sum] = "83b5a82ab97649fa065a3b99f5a5d8ce"
SRC_URI[sha256sum] = "6c5183522734539c58841703ef856096a205e54f315330d3d511a6d03f1b0840"

DEPENDS = "curl glib-2.0 json-glib openssl"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "awsclient.timer"

do_install () {
    install -d ${D}${bindir}/
    install -m 0755 ${WORKDIR}/awsclient_${PV}_aarch64 ${D}${bindir}/awsclient

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/awsclient.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/awsclient.timer ${D}${systemd_unitdir}/system/
}

FILES_${PN} += " \
    ${systemd_unitdir}/system/awsclient.service \
    ${systemd_unitdir}/system/awsclient.timer \
"

INSANE_SKIP_${PN}_append = "already-stripped"

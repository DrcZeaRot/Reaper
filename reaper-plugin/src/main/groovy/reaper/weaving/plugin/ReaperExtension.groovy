package reaper.weaving.plugin

class ReaperExtension {
    def enabled = true

    def setEnabled(boolean enabled) {
        this.enabled = enabled
    }

    def getEnabled() {
        return enabled;
    }

    def statisticEnable = false;

    def setStatisticEnable(boolean enable) {
        this.statisticEnable = enable;
    }

    def getStatisticEnable() {
        return statisticEnable;
    }

}
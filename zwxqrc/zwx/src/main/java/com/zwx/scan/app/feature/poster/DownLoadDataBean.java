package com.zwx.scan.app.feature.poster;

class DownLoadDataBean {
    private Long id;
    private boolean state;

    public DownLoadDataBean(Long id, boolean state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

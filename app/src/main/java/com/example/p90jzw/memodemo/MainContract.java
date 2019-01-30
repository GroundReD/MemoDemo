package com.example.p90jzw.memodemo;

public interface MainContract {

    interface View {
        void setPresenter(Presenter presenter);
    }

    interface Presenter {
        public void setView();
    }
}

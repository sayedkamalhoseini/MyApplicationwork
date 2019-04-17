package com.example.kamal.saatzanhamrah.MainPackage;

class MainPresenter {
    private MainModel mainModel;

    public void BuyPresenter(MainActivity mainActivity, String urlConfirm) {
        mainModel=new MainModel();
        mainModel.buyModel(mainActivity,urlConfirm);
    }

    public void result(MainActivity mainActivity, String my_buy) {
        mainActivity.confirm(my_buy);
    }
}

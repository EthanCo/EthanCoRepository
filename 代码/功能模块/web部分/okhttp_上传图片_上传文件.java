@Override
    public Observable<Base> uploadImage(final File file) {
        return observable(new Observable.OnSubscribe<Base>() {
            @Override
            public void call(Subscriber<? super Base> subscriber) {
                try {
                    String fileType = getFileType(file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("upload_file", file.getName(), RequestBody.create(MediaType.parse("image/" + fileType), file))
                            .build();
                    String result = NetClient.get().postByRequestBody(UPLOAD_IMAGE, requestBody);
                    L.i(Http_Log, result);
                    Base model = parseToBaseModel(result);
                    if (model.getReturnNo().equals(NetUtils.Return_Success)) {
                        subscriber.onNext(model);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(getErrorMsg(model.getReturnInfo()));
                    }
                } catch (IOException e) {
                    subscriber.onError(getErrorMsg("Õ¯¬Á“Ï≥£"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

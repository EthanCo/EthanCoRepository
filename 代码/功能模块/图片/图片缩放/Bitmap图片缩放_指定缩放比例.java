
   //按比例缩放图片，scale:缩放为1/scale大小
    public static Bitmap convertToBItmap(String path, int scale) {
        return BitmapFactory.decodeFile(path, getBitmapOption(scale));
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return options;
    }
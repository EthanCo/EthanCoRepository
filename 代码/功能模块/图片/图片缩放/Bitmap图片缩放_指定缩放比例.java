
   //����������ͼƬ��scale:����Ϊ1/scale��С
    public static Bitmap convertToBItmap(String path, int scale) {
        return BitmapFactory.decodeFile(path, getBitmapOption(scale));
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return options;
    }
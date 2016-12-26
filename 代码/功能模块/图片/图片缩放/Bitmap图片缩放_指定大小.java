/**
     * ת����bitmap,��ָ����С
     *
     * @param path ·��
     * @param w    ���ź�Ŀ��
     * @param h    ���ź�ĸ߶�
     * @return bitmap
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // ʹ��BitmapFactory.Options����inJustDecodeBoundsΪtrue����ʹ��decodeFile()�ȷ����������������ķ���ռ䣬�����������BitmapΪnull�����ǿɼ����ԭʼͼƬ�Ŀ�Ⱥ͸߶�,��options.outWidth�� options.outHeight��ͨ��������ֵ���Ϳ���֪��ͼƬ�Ƿ�����ˡ�
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // ����Ϊ��
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 1f, scaleHeight = 1f;
        if (width > w || height > h) {
            // ����
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
		//���´�ʹ��BitmapFactory�� decodeFile()�ȷ���ʵ����Bitmap����ǰ�������ǽ�opts.inJustDecodeBound���û�false�������ȡ�� bitmap������null��
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);//�Ƚ����ŵĿ�ߣ�ȡ���ֵ
        opts.inSampleSize = (int) scale; //����СΪ1/scale
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeStream(is, null, opts)); //������Ϊscale�ı���
        return Bitmap.createScaledBitmap(weak.get(), w, h, true); //����СΪ��Ϊw,��Ϊh����λͼ
    }
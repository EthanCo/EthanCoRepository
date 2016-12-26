InputStream is = conn.getInputStream();
String name = path.substring(path.lastIndexOf("/") + 1);
File file = new File(Environment.getExternalStorageDirectory(),name);
FileOutputStream fos = new FileOutputStream(file);
byte[] buffer = new byte[1024];
int len = 0;
while((len = is.read(buffer)) != -1){
	fos.write(buffer, 0, len);
}
fos.close();
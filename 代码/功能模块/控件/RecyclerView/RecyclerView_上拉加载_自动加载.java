recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
	@Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
		int totalItemCount = mLayoutManager.getItemCount();//lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
		// dy>0 表示向下滑动
		if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
			if(isLoadingMore){
				 Log.d(TAG,"ignore manually update!");
			} else{
				 loadPage();//这里多线程也要手动控制isLoadingMore
				isLoadingMore = false;
			}
        }
    }
});
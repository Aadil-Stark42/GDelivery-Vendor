package in.vdeliverzvendor.utils;


import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        Log.i("TESTCALLS","TESTCALLS"+"TESTLIST");
    }

    /*@Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.i("TESTCALLS","TESTCALLS"+"layoutManager"+layoutManager);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            Log.i("TESTCALLS","TESTCALLS"+"1"+visibleItemCount+"-"+firstVisibleItemPosition+"-"+totalItemCount);
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0
                    && totalItemCount >= getTotalPageCount()) {
                Log.i("TESTCALLS","TESTCALLS"+"2");
                loadMoreItems();
            }
        }

    }*/

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            Log.i("TESTCALLS","TESTCALLS"+"1"+visibleItemCount+"-"+firstVisibleItemPosition+
                    "-"+totalItemCount+"-"+getTotalPageCount());
//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0
//                    && totalItemCount >= getTotalPageCount()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                Log.i("TESTCALLS","TESTCALLS"+"2");
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}

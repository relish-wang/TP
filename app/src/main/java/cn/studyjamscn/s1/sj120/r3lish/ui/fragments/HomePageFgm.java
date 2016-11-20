package cn.studyjamscn.s1.sj120.r3lish.ui.fragments;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import cn.studyjamscn.s1.sj120.r3lish.App;
import cn.studyjamscn.s1.sj120.r3lish.R;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseFgm;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseHolder;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.OrderEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.GetOrderListRequest;
import cn.studyjamscn.s1.sj120.r3lish.networks.TakeOrderRequest;
import cn.studyjamscn.s1.sj120.r3lish.ui.activities.MainAty;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppLog;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;

/**
 * 发布列表
 * <p>
 * Created by r3lis on 2016/3/13.
 */
public class HomePageFgm extends BaseFgm {

//    @Bind(R.id.tvNoData)
//    TextView tvNoData;

    @Bind(R.id.rvOrders)
    RecyclerView rvOrders;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayoutManager llm;
    RvAdapter adapter;

    int currentSize = 0;
    int pageSize = 50;

    ArrayList<OrderEntity> orders;


    @Override
    protected int layoutResId() {
        return R.layout.fgm_home_page;
    }


    @Override
    protected void initView() {
        currentSize = 0;
        orders = new ArrayList<>();
        adapter = new RvAdapter();
        llm = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvOrders.setLayoutManager(llm);
        //设置adapter
        rvOrders.setAdapter(adapter);
        //设置Item增加、移除动画
        rvOrders.setItemAnimator(new DefaultItemAnimator());
        //noinspection deprecation
        rvOrders.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = llm.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount() && (rvOrders.isLayoutFrozen())) {
                    ((MainAty) getActivity()).fab.setVisibility(View.GONE);
                } else {
                    ((MainAty) getActivity()).fab.setVisibility(View.VISIBLE);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        requestData();
    }


    /**
     * 获取数据
     */

    public void requestData() {
        GetOrderListRequest request = new GetOrderListRequest(0, pageSize);
        request.setOnResponseListener(new BaseRequest.OnResponseListener<ArrayList<OrderEntity>>() {
            @Override
            public void onSuccess(ArrayList<OrderEntity> orderEntities) {
                orders = new ArrayList<>();
                orders.addAll(orderEntities);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyItemRemoved(adapter.getItemCount());
            }

            @Override
            public void onFail(String message) {
                swipeRefreshLayout.setRefreshing(false);
                if (TextUtils.equals(message, "返回数据解析错误")) {
                    AppToast.showShort("暂无数据");
                    return;
                }
                AppToast.showShort(message);
            }
        });
        request.request();
    }


    /**
     * 订单列表适配器
     */
    class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        public RvAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(App.CONTEXT).inflate(R.layout.rv_item_homepage, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final OrderEntity order = orders.get(position);
            holder.tvName.setText(order.getName());
            AppLog.d("HomePageFgm", "onBindViewHolder", order.getLogo().toString());
            holder.ivHead.setImageBitmap(order.getLogo());
            holder.tvTimeGet.setText(order.getTimeGet());
            holder.tvTimeSend.setText(order.getTimeSend());
            holder.tvAddressFrom.setText(order.getDestination());
            holder.tvAddressTo.setText(order.getSource());
            holder.tvCost.setText(String.valueOf(order.getCost()));
            holder.tvTookOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("接单确认")
                            .setMessage("你确认进行这次传送吗？\n注意：确认接单后，系统会将你的一些个人信息发送给发布者，以作联系使用。")
                            .setPositiveButton("接单", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showLoading(true);
                                    TakeOrderRequest request = new TakeOrderRequest(order.getId());
                                    request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            orders.remove(position);
                                            showLoading(false);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFail(String message) {
                                            showLoading(false);
                                            AppToast.showShort(message);
                                        }
                                    });
                                    request.request();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends BaseHolder {

            @Bind(R.id.ivHead)
            ImageView ivHead;
            @Bind(R.id.tvName)
            TextView tvName;
            @Bind(R.id.tvAddressFrom)
            TextView tvAddressFrom;
            @Bind(R.id.tvAddressTo)
            TextView tvAddressTo;
            @Bind(R.id.tvTimeGet)
            TextView tvTimeGet;
            @Bind(R.id.tvTimeSend)
            TextView tvTimeSend;
            @Bind(R.id.tvWarming)
            TextView tvWarming;
            @Bind(R.id.tvCost)
            TextView tvCost;

            @Bind(R.id.tvMoreDetails)
            TextView tvMoreDetails;
            @Bind(R.id.tvTakeOrder)
            TextView tvTookOrder;

            boolean isShow = false;

            public ViewHolder(View view) {
                super(view);
                tvMoreDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMore();
                    }
                });
            }

            public void showMore() {
                if (isShow) {
                    tvTimeGet.setVisibility(View.GONE);
                    tvTimeSend.setVisibility(View.GONE);
                    tvWarming.setVisibility(View.GONE);
                    tvMoreDetails.setText("详情");
                } else {
                    tvTimeGet.setVisibility(View.VISIBLE);
                    tvTimeSend.setVisibility(View.VISIBLE);
                    tvWarming.setVisibility(View.VISIBLE);
                    tvMoreDetails.setText("收起");
                }
                isShow = !isShow;
            }
        }
    }
}

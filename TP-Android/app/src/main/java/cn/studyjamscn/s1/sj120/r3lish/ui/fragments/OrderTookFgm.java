package cn.studyjamscn.s1.sj120.r3lish.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import cn.studyjamscn.s1.sj120.r3lish.networks.GetOrderDetailRequest;
import cn.studyjamscn.s1.sj120.r3lish.networks.GetTookOrderListRequest;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * 我接的单
 * <p/>
 * Created by r3lis on 2016/4/5.
 */
public class OrderTookFgm extends BaseFgm {

    @Bind(R.id.rvTookOrders)
    RecyclerView rvTookOrders;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayoutManager llm;
    RvAdapter adapter;

    int currentSize = 0;
    int pageSize = 50;

    ArrayList<OrderEntity> orders;
    boolean noMoreData = false;

    boolean isLoading;

    @Override
    protected int layoutResId() {
        return R.layout.fgm_order_took;
    }

    @Override
    protected void initView() {
        currentSize = 0;
        orders = new ArrayList<>();
        adapter = new RvAdapter();
        llm = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvTookOrders.setLayoutManager(llm);
        //设置adapter
        rvTookOrders.setAdapter(adapter);
        //设置Item增加、移除动画
        rvTookOrders.setItemAnimator(new DefaultItemAnimator());
//        rvTookOrders.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int lastVisibleItemPosition = llm.findLastVisibleItemPosition();
//                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
//                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
//                    if (isRefreshing) {
//                        adapter.notifyItemRemoved(adapter.getItemCount());
//                        return;
//                    }
//                    if (!isLoading) {
//                        noMoreData = true;
//                    }
//                }
//            }
//        });
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

    private void requestData() {

        GetTookOrderListRequest request = new GetTookOrderListRequest(0, pageSize);
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
            return new ViewHolder(LayoutInflater.from(App.CONTEXT).inflate(R.layout.rv_item_order_took, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final OrderEntity order = orders.get(position);

            holder.tvName.setText(order.getName());
            holder.tvUserA.setText(order.getUserA());
            holder.ivHead.setImageBitmap(AppUtil.toRoundBitmap(order.getLogo()));
            holder.tvTimeGet.setText(order.getTimeGet());
            holder.tvTimeSend.setText(order.getTimeSend());
            holder.tvAddressFrom.setText(order.getSource());
            holder.tvAddressTo.setText(order.getDestination());
            holder.tvContent.setText(order.getContent());
            holder.tvUserA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + order.getUserA());
                    intent.setData(data);
                    startActivity(intent);
                }
            });
            holder.tvMoreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.isShow) {
                        holder.tvTimeSend.setVisibility(View.GONE);
                        holder.tvTimeGet.setVisibility(View.GONE);
                        holder.tvContent.setVisibility(View.GONE);
                        holder.tvUserA.setVisibility(View.GONE);
                        holder.tvMoreDetails.setText("详  情");
                    } else {
                        if (order.isMore()) {
                            holder.tvTimeSend.setVisibility(View.VISIBLE);
                            holder.tvTimeGet.setVisibility(View.VISIBLE);
                            holder.tvContent.setVisibility(View.VISIBLE);
                            holder.tvUserA.setVisibility(View.VISIBLE);
                            holder.tvMoreDetails.setText("收  起");
                        } else {
                            GetOrderDetailRequest request = new GetOrderDetailRequest(order.getId());
                            request.setOnResponseListener(new BaseRequest.OnResponseListener<OrderEntity>() {
                                @Override
                                public void onSuccess(OrderEntity orderEntity) {
                                    orders.get(position).setIsMore(true);
                                    orders.get(position).setUserA(orderEntity.getUserA());
                                    orders.get(position).setUserB(orderEntity.getUserB());
                                    orders.get(position).setContent(orderEntity.getContent());

                                    holder.tvContent.setText(orderEntity.getContent());
                                    holder.tvUserA.setText(order.getUserA());
                                    holder.tvContent.setVisibility(View.VISIBLE);
                                    holder.tvTimeSend.setVisibility(View.VISIBLE);
                                    holder.tvTimeGet.setVisibility(View.VISIBLE);
                                    holder.tvUserA.setVisibility(View.VISIBLE);
                                    holder.tvMoreDetails.setText("收  起");
                                }

                                @Override
                                public void onFail(String message) {
                                    AppToast.showShort(message);
                                }
                            });
                            request.request();
                        }
                    }
                    holder.isShow = !holder.isShow;
                }
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends BaseHolder {

            @Bind(R.id.tvName)
            TextView tvName;

            @Bind(R.id.ivHead)
            ImageView ivHead;

            @Bind(R.id.tvUserA)
            TextView tvUserA;

            @Bind(R.id.tvAddressFrom)
            TextView tvAddressFrom;
            @Bind(R.id.tvAddressTo)
            TextView tvAddressTo;
            @Bind(R.id.tvTimeGet)
            TextView tvTimeGet;
            @Bind(R.id.tvTimeSend)
            TextView tvTimeSend;

            @Bind(R.id.tvMoreDetails)
            TextView tvMoreDetails;

            @Bind(R.id.tvContent)
            TextView tvContent;

            boolean isShow = false;

            public ViewHolder(View view) {
                super(view);
                tvContent.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
            }
        }
    }
}

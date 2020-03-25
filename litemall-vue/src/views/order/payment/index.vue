<template>
  <div id="app">
    <h3 class="index-title">模拟的密码为：111111</h3>

    <div class="submit-btn" @click="onShowPay">支付</div>

    <!--支付密码弹窗-->
    <vpay
      ref="pays"
      v-model="show"
      @close="close"
      @forget="forget"
      @input-end="inputEnd"
    ></vpay>
  </div>
</template>

<script>
  import { Radio, RadioGroup, Dialog } from 'vant';
  import { orderDetail, orderPrepay, orderH5pay } from '@/api/api';
  import _ from 'lodash';
  import { getLocalStorage, setLocalStorage } from '@/utils/local-storage';

  export default {
    name: 'App',

    data () {
      return {
        show: false,
        order: {
          orderInfo: {},
          orderGoods: []
        },
        orderId: 0
      }
    },
    created() {
      if (_.has(this.$route.params, 'orderId')) {
        this.orderId = this.$route.params.orderId;
        this.getOrder(this.orderId);
      }
    },

    methods: {
      getOrder(orderId) {
        orderDetail({orderId: orderId}).then(res => {
          this.order = res.data.data;
        });
      },

      onShowPay () {
        this.show = true;
      },

      // 密码输入完成回调
      inputEnd (val) {
        setTimeout(() => {

          // 模拟支付成功的结果
          if (val != null){
            if (val == 111111) {
              // 调用插件的$success方法告知插件支付成功
              // 并且在then方法里面可以写支付成功的回调，例如可以跳转支付结果页面
              this.$refs.pays.$success().then(res => {
                console.log('支付成功');
                //this.$router.push('/user')
                orderH5pay({ orderId: this.orderId })
                        .then(res => {
                          let data = res.data.data;
                          window.location.replace(
                                  data.mwebUrl +
                                  '&redirect_url=' +
                                  encodeURIComponent(
                                          window.location.origin +
                                          '/#/?orderId=' +
                                          this.orderId +
                                          '&tip=yes'
                                  )
                          );
                        }).catch();    //新加catch
                this.$router.replace({
                  name: 'paymentStatus',
                  params: {
                    status: 'success'
                  }
                });
                // .catch(err => {
                //     Dialog.alert({ message: err.data.errmsg });
                //   })
                //this.$router.replace({path:'/wx/order/payCheck',params:orderDetail({ orderId: orderId })})
              }).catch()
              console.log("orderID: "+this.orderId);
              // 模拟支付失败的结果
            } else {
              this.$refs.pays.$fail();
            }
          }



        }, 1000)
      },

      // 取消支付弹窗关闭的回调
      close() {
        console.log('关闭')
      },

      // 忘记密码跳转
      forget () {
        console.log('触发forge事件');
      }
    }
  }
</script>

<style>
  * {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    user-select: none;
  }
  body {
    max-width: 750px;
    margin: 0 auto;
  }
  #app {
    min-height: 100vh;
    background: #f1f1f1;
  }
  .index-title {
    padding-top: 50px;
    font-weight: normal;
    text-align: center;
  }
  .submit-btn {
    width: 50%;
    height: 50px;
    margin: 100px auto 0;
    line-height: 50px;
    text-align: center;
    border-radius: 10px;
    background: #63B8FF;
    color: #fff;
    cursor: pointer;
  }
</style>

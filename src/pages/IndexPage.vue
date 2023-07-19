<template>
  <q-page class="">
    <div class="flex flex-center">
      <div class="q-pa-md q-gutter-md">
        <q-btn color="deep-orange" align="around" class="glossy" @click="showTextLoading" icon="notifications"
               label="GET FCM Device Token">
        </q-btn>
        <q-card class="card-example" flat bordered>
          <q-card-section class="q-pb-none">
            <div class="text-h6">Your FCM Device Token</div>
          </q-card-section>

          <q-card-section>
            <transition
              appear
              enter-active-class="animated fadeIn"
              leave-active-class="animated fadeOut"
            >
              <div class="" v-show="showSimulatedReturnData && deviceToken">
                {{ deviceToken }}
              </div>
            </transition>
          </q-card-section>

          <q-inner-loading
            :showing="visible"
            label="Please wait..."
            label-class="text-teal"
            label-style="font-size: 1.1em"
          />
        </q-card>
      </div>
      <img class="flex flex-center"
           alt="Quasar logo"
           src="~assets/quasar-logo-vertical.svg"
           style="width: 200px; height: 200px">
    </div>
  </q-page>
</template>

<script>
import { defineComponent } from 'vue';
import { ref } from 'vue';
import { Plugins } from '@capacitor/core';
import axios from "axios";

const { HelperClassPlugin } = Plugins;

export default defineComponent({
  name: 'IndexPage',
  setup() {
    const visible = ref(false);
    const showSimulatedReturnData = ref(false);
    const deviceToken = ref('');

    async function showTextLoading() {
      try {
        const value = await HelperClassPlugin.getFirebaseToken();
        console.log('Token received:', value.token);

        visible.value = true;
        showSimulatedReturnData.value = false;
        deviceToken.value = value.token;

        await new Promise((resolve) => {
          setTimeout(() => {
            visible.value = false;
            showSimulatedReturnData.value = true;
            resolve();
          }, 3000);
        });
      } catch (err) {
        console.error('ERROR OCCURRED:', err);
        alert("ERROR OCCURRED: " + JSON.stringify(err));
      }
    }

    return {
      visible,
      showSimulatedReturnData,
      deviceToken,
      showTextLoading,
    };
  },
});
</script>

<style lang="sass" scoped>
.card-example
  width: 310px
  height: 300px
  overflow: auto
  padding: 16px
  word-wrap: break-word
  word-break: break-word
  white-space: normal
</style>

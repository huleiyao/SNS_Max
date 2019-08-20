/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bytegem.snsmax.main.app;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {
    //String APP_DOMAIN = "http://132.232.134.226:8002";
    String APP_DOMAIN = "https://snsmax.bytegem.net";
    String FILE_UPDATA_DOMAIN_NAME = "file-updata";
    String FILE_UPDATA_DOMAIN = "https://bytegem-1251005678.cos.ap-chengdu.myqcloud.com";
    String FILE_LOOK_DOMAIN = "https://bytegem-1251005678.image.myqcloud.com";
}

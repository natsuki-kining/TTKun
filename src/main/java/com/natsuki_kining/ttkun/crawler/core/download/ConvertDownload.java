package com.natsuki_kining.ttkun.crawler.core.download;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/3/2 16:28
 **/
public class ConvertDownload {

    /*public void MyBackgroundTask() {
        this._downloadDir = Path.GetDirectoryName(Assembly.GetEntryAssembly().Location) + "\\out";
        Directory.CreateDirectory(this._downloadDir);
        this.CurrentProgress = 0.0;
        bool flag = !this._tbCId.Equals("");
        if (flag) {
            WebClient webClient = new WebClient();
            object arg = webClient.DownloadString("https://ssl.seiga.nicovideo.jp/api/v1/comicwalker/episodes/" + this._tbCId + "/frames");
            if (MainViewModel.<>o__13.<>p__1 == null) {
                MainViewModel.<>o__13.<>p__1 = CallSite < Func < CallSite, object, JObject >>.
                Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(JObject), typeof(MainViewModel)));
            }
            Func<CallSite, object, JObject> arg_118_0 = MainViewModel.<>o__13.<>p__1.Target;
            CallSite arg_118_1 = MainViewModel.<>o__13.<>p__1;
            if (MainViewModel.<>o__13.<>p__0 == null) {
                MainViewModel.<>o__13.<>p__0 = CallSite < Func < CallSite, Type, object, object >>.
                Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(MainViewModel), new CSharpArgumentInfo[]
                        {
                                CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
                                CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
                        }));
            }
            JObject jObject = arg_118_0(arg_118_1, MainViewModel.<>o__13.<>p__0.Target(MainViewModel.<>o__13.<>p__0, typeof(JObject), arg));
            JObject jObject2 = (JObject) jObject.get_Item("data");
            JArray jArray = (JArray) jObject2.get_Item("result");
            bool flag2 = jArray == null;
            if (flag2) {
                this.LblInfo = "CID Error";
            } else {
                this.BtnTxtDownload = "Stop";
                this.LblInfo = "Start";
                this._running = true;
                int count = jArray.get_Count();
                double num = 0.0;
                foreach(JObject current in jArray.Children < JObject > ())
                {
                    num += 1.0;
                    this.CurrentProgress = num / (double) count;
                    Console.WriteLine(num + "/" + count);
                    Console.WriteLine(this.CurrentProgress);
                    JObject jObject3 = (JObject) current.get_Item("meta");
                    string text = jObject3.get_Item("source_url").ToString();
                    string text2 = jObject3.get_Item("drm_hash").ToString();
                    bool flag3 = !this._running;
                    if (flag3) {
                        break;
                    }
                    text = MainViewModel.RemoveDeplicatedChar(text);
                    this.LblInfo = "Download frame" + num + " ...";
                    byte[] data = webClient.DownloadData(text);
                    this.LblInfo = "Decode frame" + num + " ...";
                    string hexString = text2.Substring(0, 16);
                    byte[] key = MainViewModel.ToByteArray(hexString);
                    byte[] bytes = MainViewModel.XORTwoBytes(data, key);
                    File.WriteAllBytes(string.Concat(new object[]
                            {
                                    this._downloadDir,
                                    "\\",
                                    num,
                                    ".jpg"
                            }), bytes);
                }
                this.BtnTxtDownload = "Download";
                this._running = false;
                this.LblInfo = "Total frames:" + num;
            }
        } else {
            this.LblInfo = "Input Error";
        }
    }

    public static byte[] XORTwoBytes(byte[] data, byte[] key) {
        int num = data.Length;
        int num2 = key.Length;
        byte[] array = new byte[num];
        for (int i = 0; i < num; i++) {
            array[i] = (data[i] ^ key[i % num2]);
        }
        return array;
    }

    public static string RemoveDeplicatedChar(string sourceUrl) {
        return sourceUrl.Replace("\\", "");
    }

    public static byte[] ToByteArray(string HexString) {
        int length = HexString.Length;
        byte[] array = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            array[i / 2] = Convert.ToByte(HexString.Substring(i, 2), 16);
        }
        return array;
    }*/

}

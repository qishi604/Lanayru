//package com.lanayru.app.ui.media;
//
//import android.media.MediaCodec;
//import android.media.MediaExtractor;
//import android.media.MediaFormat;
//import android.text.TextUtils;
//
//import com.lanayru.util.Logs;
//
//import java.io.BufferedOutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//
//public class MediaUtil {
//
//    interface DecodeOperateInterface {
//
//    }
//
//    static class Constant {
//        private static final int  OneSecond = 1;
//        private static final int  NormalMaxProgress = 100;
//        private static final int  ExportSampleRate = 44100;
//
//    }
//
//    /**
//     * 将音乐文件解码
//     *
//     * @param musicFileUrl 源文件路径
//     * @param decodeFileUrl 解码文件路径
//     * @param startMicroseconds 开始时间 微秒
//     * @param endMicroseconds 结束时间 微秒
//     * @param decodeOperateInterface 解码过程回调
//     */
//    private boolean decodeMusicFile(String musicFileUrl, String decodeFileUrl,
//                                    long startMicroseconds, long endMicroseconds, DecodeOperateInterface decodeOperateInterface) {
//
//        //采样率，声道数，时长，音频文件类型
//        int sampleRate = 0;
//        int channelCount = 0;
//        long duration = 0;
//        String mime = null;
//
//        //MediaExtractor, MediaFormat, MediaCodec
//        MediaExtractor mediaExtractor = new MediaExtractor();
//        MediaFormat mediaFormat = null;
//        MediaCodec mediaCodec = null;
//
//        //给媒体信息提取器设置源音频文件路径
//        try {
//            mediaExtractor.setDataSource(musicFileUrl);
//        }catch (Exception ex){
//            ex.printStackTrace();
//            try {
//                mediaExtractor.setDataSource(new FileInputStream(musicFileUrl).getFD());
//            } catch (Exception e) {
//                e.printStackTrace();
//                Logs.INSTANCE.e("设置解码音频文件路径错误");
//            }
//        }
//
//        //获取音频格式轨信息
//        mediaFormat = mediaExtractor.getTrackFormat(0);
//
//        //从音频格式轨信息中读取 采样率，声道数，时长，音频文件类型
//        sampleRate = mediaFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE) ? mediaFormat.getInteger(
//                MediaFormat.KEY_SAMPLE_RATE) : 44100;
//        channelCount = mediaFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT) ? mediaFormat.getInteger(
//                MediaFormat.KEY_CHANNEL_COUNT) : 1;
//        duration = mediaFormat.containsKey(MediaFormat.KEY_DURATION) ? mediaFormat.getLong(
//                MediaFormat.KEY_DURATION) : 0;
//        mime = mediaFormat.containsKey(MediaFormat.KEY_MIME) ? mediaFormat.getString(MediaFormat.KEY_MIME)
//                : "";
//
//        Logs.INSTANCE.e("歌曲信息Track info: mime:"
//                + mime
//                + " 采样率sampleRate:"
//                + sampleRate
//                + " channels:"
//                + channelCount
//                + " duration:"
//                + duration);
//
//        if (TextUtils.isEmpty(mime) || !mime.startsWith("audio/")) {
//            Logs.INSTANCE.e("解码文件不是音频文件mime:" + mime);
//            return false;
//        }
//
//        if (mime.equals("audio/ffmpeg")) {
//            mime = "audio/mpeg";
//            mediaFormat.setString(MediaFormat.KEY_MIME, mime);
//        }
//
//        if (duration <= 0) {
//            Logs.INSTANCE.e("音频文件duration为" + duration);
//            return false;
//        }
//
//        //解码的开始时间和结束时间
//        startMicroseconds = Math.max(startMicroseconds, 0);
//        endMicroseconds = endMicroseconds < 0 ? duration : endMicroseconds;
//        endMicroseconds = Math.min(endMicroseconds, duration);
//
//        if (startMicroseconds >= endMicroseconds) {
//            return false;
//        }
//
//        //创建一个解码器
//        try {
//            mediaCodec = MediaCodec.createDecoderByType(mime);
//
//            mediaCodec.configure(mediaFormat, null, null, 0);
//        } catch (Exception e) {
//            Logs.INSTANCE.e("解码器configure出错");
//            return false;
//        }
//
//        //得到输出PCM文件的路径
//        decodeFileUrl = decodeFileUrl.substring(0, decodeFileUrl.lastIndexOf("."));
//        String pcmFilePath = decodeFileUrl + ".pcm";
//
//        //后续解码操作
//        getDecodeData(mediaExtractor, mediaCodec, pcmFilePath, sampleRate, channelCount,
//                startMicroseconds, endMicroseconds, decodeOperateInterface);
//
//        return true;
//    }
//    /**
//     * 解码数据
//     */
//    private void getDecodeData(MediaExtractor mediaExtractor, MediaCodec mediaCodec,
//                               String decodeFileUrl, int sampleRate, int channelCount, final long startMicroseconds,
//                               final long endMicroseconds, final DecodeOperateInterface decodeOperateInterface) {
//
//        //初始化解码状态，未解析完成
//        boolean decodeInputEnd = false;
//        boolean decodeOutputEnd = false;
//
//        //当前读取采样数据的大小
//        int sampleDataSize;
//        //当前输入数据的ByteBuffer序号，当前输出数据的ByteBuffer序号
//        int inputBufferIndex;
//        int outputBufferIndex;
//        //音频文件的采样位数字节数，= 采样位数/8
//        int byteNumber;
//
//        //上一次的解码操作时间，当前解码操作时间，用于通知回调接口
//        long decodeNoticeTime = System.currentTimeMillis();
//        long decodeTime;
//
//        //当前采样的音频时间，比如在当前音频的第40秒的时候
//        long presentationTimeUs = 0;
//
//        //定义编解码的超时时间
//        final long timeOutUs = 100;
//
//        //存储输入数据的ByteBuffer数组，输出数据的ByteBuffer数组
//        ByteBuffer[] inputBuffers;
//        ByteBuffer[] outputBuffers;
//
//        //当前编解码器操作的 输入数据ByteBuffer 和 输出数据ByteBuffer，可以从targetBuffer中获取解码后的PCM数据
//        ByteBuffer sourceBuffer;
//        ByteBuffer targetBuffer;
//
//        //获取输出音频的媒体格式信息
//        MediaFormat outputFormat = mediaCodec.getOutputFormat();
//
//        MediaCodec.BufferInfo bufferInfo;
//
//        byteNumber = (outputFormat.containsKey("bit-width") ? outputFormat.getInteger("bit-width") : 0) / 8;
//
//        //开始解码操作
//        mediaCodec.start();
//
//        //获取存储输入数据的ByteBuffer数组，输出数据的ByteBuffer数组
//        inputBuffers = mediaCodec.getInputBuffers();
//        outputBuffers = mediaCodec.getOutputBuffers();
//
//        mediaExtractor.selectTrack(0);
//
//        //当前解码的缓存信息，里面的有效数据在offset和offset+size之间
//        bufferInfo = new MediaCodec.BufferInfo();
//
//        //获取解码后文件的输出流
//        BufferedOutputStream bufferedOutputStream =
//                FileFunction.getBufferedOutputStreamFromFile(decodeFileUrl);
//
//        //开始进入循环解码操作，判断读入源音频数据是否完成，输出解码音频数据是否完成
//        while (!decodeOutputEnd) {
//            if (decodeInputEnd) {
//                return;
//            }
//
//            decodeTime = System.currentTimeMillis();
//
//            //间隔1秒通知解码进度
//            if (decodeTime - decodeNoticeTime > Constant.OneSecond) {
//                final int decodeProgress =
//                        (int) ((presentationTimeUs - startMicroseconds) * Constant.NormalMaxProgress
//                                / endMicroseconds);
//
//                if (decodeProgress > 0) {
//                    notifyProgress(decodeOperateInterface, decodeProgress);
//                }
//
//                decodeNoticeTime = decodeTime;
//            }
//
//            try {
//
//                //操作解码输入数据
//
//                //从队列中获取当前解码器处理输入数据的ByteBuffer序号
//                inputBufferIndex = mediaCodec.dequeueInputBuffer(timeOutUs);
//
//                if (inputBufferIndex >= 0) {
//                    //取得当前解码器处理输入数据的ByteBuffer
//                    sourceBuffer = inputBuffers[inputBufferIndex];
//                    //获取当前ByteBuffer，编解码器读取了多少采样数据
//                    sampleDataSize = mediaExtractor.readSampleData(sourceBuffer, 0);
//
//                    //如果当前读取的采样数据<0，说明已经完成了读取操作
//                    if (sampleDataSize < 0) {
//                        decodeInputEnd = true;
//                        sampleDataSize = 0;
//                    } else {
//                        presentationTimeUs = mediaExtractor.getSampleTime();
//                    }
//
//                    //然后将当前ByteBuffer重新加入到队列中交给编解码器做下一步读取操作
//                    mediaCodec.queueInputBuffer(inputBufferIndex, 0, sampleDataSize, presentationTimeUs,
//                            decodeInputEnd ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0);
//
//                    //前进到下一段采样数据
//                    if (!decodeInputEnd) {
//                        mediaExtractor.advance();
//                    }
//
//                } else {
//                    //Logs.INSTANCE.e("inputBufferIndex" + inputBufferIndex);
//                }
//
//                //操作解码输出数据
//
//                //从队列中获取当前解码器处理输出数据的ByteBuffer序号
//                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, timeOutUs);
//
//                if (outputBufferIndex < 0) {
//                    //输出ByteBuffer序号<0，可能是输出缓存变化了，输出格式信息变化了
//                    switch (outputBufferIndex) {
//                        case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
//                            outputBuffers = mediaCodec.getOutputBuffers();
//                            Logs.INSTANCE.e(
//                                    "MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED [AudioDecoder]output buffers have changed.");
//                            break;
//                        case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
//                            outputFormat = mediaCodec.getOutputFormat();
//
//                            sampleRate =
//                                    outputFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE) ? outputFormat.getInteger(
//                                            MediaFormat.KEY_SAMPLE_RATE) : sampleRate;
//                            channelCount =
//                                    outputFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT) ? outputFormat.getInteger(
//                                            MediaFormat.KEY_CHANNEL_COUNT) : channelCount;
//                            byteNumber =
//                                    (outputFormat.containsKey("bit-width") ? outputFormat.getInteger("bit-width") : 0)
//                                            / 8;
//
//                            Logs.INSTANCE.e(
//                                    "MediaCodec.INFO_OUTPUT_FORMAT_CHANGED [AudioDecoder]output format has changed to "
//                                            + mediaCodec.getOutputFormat());
//                            break;
//                        default:
//                            //Logs.INSTANCE.e("error [AudioDecoder] dequeueOutputBuffer returned " + outputBufferIndex);
//                            break;
//                    }
//                    continue;
//                }
//
//                //取得当前解码器处理输出数据的ByteBuffer
//                targetBuffer = outputBuffers[outputBufferIndex];
//
//                byte[] sourceByteArray = new byte[bufferInfo.size];
//
//                //将解码后的targetBuffer中的数据复制到sourceByteArray中
//                targetBuffer.get(sourceByteArray);
//                targetBuffer.clear();
//
//                //释放当前的输出缓存
//                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
//
//                //判断当前是否解码数据全部结束了
//                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//                    decodeOutputEnd = true;
//                }
//
//                //sourceByteArray就是最终解码后的采样数据
//                //接下来可以对这些数据进行采样位数，声道的转换，但这是可选的，默认是和源音频一样的声道和采样位数
//                if (sourceByteArray.length > 0 && bufferedOutputStream != null) {
//                    if (presentationTimeUs < startMicroseconds) {
//                        continue;
//                    }
//
//                    //采样位数转换，按自己需要是否实现
//                    byte[] convertByteNumberByteArray =
//                            convertByteNumber(byteNumber, Constant.ExportByteNumber, sourceByteArray);
//
//                    //声道转换，按自己需要是否实现
//                    byte[] resultByteArray = convertChannelNumber(channelCount, Constant.ExportChannelNumber,
//                            Constant.ExportByteNumber, convertByteNumberByteArray);
//
//                    //将解码后的PCM数据写入到PCM文件
//                    try {
//                        bufferedOutputStream.write(resultByteArray);
//                    } catch (Exception e) {
//                        Logs.INSTANCE.e("输出解压音频数据异常" + e);
//                    }
//                }
//
//                if (presentationTimeUs > endMicroseconds) {
//                    break;
//                }
//            } catch (Exception e) {
//                Logs.INSTANCE.e("getDecodeData异常" + e);
//            }
//        }
//
//        if (bufferedOutputStream != null) {
//            try {
//                bufferedOutputStream.close();
//            } catch (IOException e) {
//                Logs.INSTANCE.e("关闭bufferedOutputStream异常" + e);
//            }
//        }
//
//        //重置采样率，按自己需要是否实现
//        if (sampleRate != Constant.ExportSampleRate) {
//            Resample(sampleRate, decodeFileUrl);
//        }
//
//        notifyProgress(decodeOperateInterface, 100);
//
//        //释放mediaCodec 和 mediaExtractor
//        if (mediaCodec != null) {
//            mediaCodec.stop();
//            mediaCodec.release();
//        }
//
//        if (mediaExtractor != null) {
//            mediaExtractor.release();
//        }
//    }
//
//    /**
//     * PCM文件转WAV文件
//     * @param inPcmFilePath 输入PCM文件路径
//     * @param outWavFilePath 输出WAV文件路径
//     * @param sampleRate 采样率，例如44100
//     * @param channels 声道数 单声道：1或双声道：2
//     * @param bitNum 采样位数，8或16
//     */
//    public static void convertPcm2Wav(String inPcmFilePath, String outWavFilePath, int sampleRate,
//                                      int channels, int bitNum) {
//
//        FileInputStream in = null;
//        FileOutputStream out = null;
//        byte[] data = new byte[1024];
//
//        try {
//            //采样字节byte率
//            long byteRate = sampleRate * channels * bitNum / 8;
//
//            in = new FileInputStream(inPcmFilePath);
//            out = new FileOutputStream(outWavFilePath);
//
//            //PCM文件大小
//            long totalAudioLen = in.getChannel().size();
//
//            //总大小，由于不包括RIFF和WAV，所以是44 - 8 = 36，在加上PCM文件大小
//            long totalDataLen = totalAudioLen + 36;
//
//            writeWaveFileHeader(out, totalAudioLen, totalDataLen, sampleRate, channels, byteRate);
//
//            int length = 0;
//            while ((length = in.read(data)) > 0) {
//                out.write(data, 0, length);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 输出WAV文件
//     * @param out WAV输出文件流
//     * @param totalAudioLen 整个音频PCM数据大小
//     * @param totalDataLen 整个数据大小
//     * @param sampleRate 采样率
//     * @param channels 声道数
//     * @param byteRate 采样字节byte率
//     * @throws IOException
//     */
//    private static void writeWaveFileHeader(FileOutputStream out, long totalAudioLen,
//                                            long totalDataLen, int sampleRate, int channels, long byteRate) throws IOException {
//        byte[] header = new byte[44];
//        header[0] = 'R'; // RIFF
//        header[1] = 'I';
//        header[2] = 'F';
//        header[3] = 'F';
//        header[4] = (byte) (totalDataLen & 0xff);//数据大小
//        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
//        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
//        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
//        header[8] = 'W';//WAVE
//        header[9] = 'A';
//        header[10] = 'V';
//        header[11] = 'E';
//        //FMT Chunk
//        header[12] = 'f'; // 'fmt '
//        header[13] = 'm';
//        header[14] = 't';
//        header[15] = ' ';//过渡字节
//        //数据大小
//        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
//        header[17] = 0;
//        header[18] = 0;
//        header[19] = 0;
//        //编码方式 10H为PCM编码格式
//        header[20] = 1; // format = 1
//        header[21] = 0;
//        //通道数
//        header[22] = (byte) channels;
//        header[23] = 0;
//        //采样率，每个通道的播放速度
//        header[24] = (byte) (sampleRate & 0xff);
//        header[25] = (byte) ((sampleRate >> 8) & 0xff);
//        header[26] = (byte) ((sampleRate >> 16) & 0xff);
//        header[27] = (byte) ((sampleRate >> 24) & 0xff);
//        //音频数据传送速率,采样率*通道数*采样深度/8
//        header[28] = (byte) (byteRate & 0xff);
//        header[29] = (byte) ((byteRate >> 8) & 0xff);
//        header[30] = (byte) ((byteRate >> 16) & 0xff);
//        header[31] = (byte) ((byteRate >> 24) & 0xff);
//        // 确定系统一次要处理多少个这样字节的数据，确定缓冲区，通道数*采样位数
//        header[32] = (byte) (channels * 16 / 8);
//        header[33] = 0;
//        //每个样本的数据位数
//        header[34] = 16;
//        header[35] = 0;
//        //Data chunk
//        header[36] = 'd';//data
//        header[37] = 'a';
//        header[38] = 't';
//        header[39] = 'a';
//        header[40] = (byte) (totalAudioLen & 0xff);
//        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
//        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
//        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
//        out.write(header, 0, 44);
//    }
//}

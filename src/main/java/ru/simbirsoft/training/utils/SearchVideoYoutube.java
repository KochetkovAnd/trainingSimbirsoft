package ru.simbirsoft.training.utils;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.simbirsoft.training.dto.Videos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class SearchVideoYoutube {

    private String apikey;
    private YouTube youTube;

    public SearchVideoYoutube(@Value("${youtube.apikey}") String apikey) {
        if (youTube == null) {
            youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {

                }
            }).setApplicationName("chat-youtube-api").build();
        }
        this.apikey = apikey;
    }

    public String getChannelId(String channel) throws Throwable {
        String channelId = null;
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
        search.setQ(channel);
        search.setType("channel");
        search.setMaxResults(50l);

        SearchListResponse searchListResponse = search.execute();
        List<SearchResult> searchResultList = searchListResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> iterator = searchResultList.iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    SearchResult singleResult  = iterator.next();
                    if (singleResult.getId().getKind().equals("youtube#channel") && singleResult.getSnippet().getTitle().equals(channel)) {
                        channelId = singleResult.getId().getChannelId();
                        break;
                    }
                }
            }
        }
        return  channelId;
    }

    public List<Videos> getVideoList(String movie, String channelId, Long resultCount, Boolean sort, boolean likes, boolean views) throws Throwable {

        List<Videos> videosList = new ArrayList<>();

        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
        if (!movie.isEmpty()) {
            search.setQ(movie);
        }
        search.setType("video");

        if (sort) {
            search.setOrder("date");
        }

        if (!channelId.isEmpty()) {
            search.setChannelId(channelId);
        }

        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/publishedAt)");
        if (resultCount > 0) {
            search.setMaxResults(resultCount);
        }

        SearchListResponse searchListResponse = search.execute();
        List<SearchResult> searchResultList = searchListResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> iterator = searchResultList.iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {

                    SearchResult singleVideo = iterator.next();
                    ResourceId resourceId = singleVideo.getId();

                    if (resourceId.getKind().equals("youtube#video")) {
                        Videos videos = new Videos();
                        videos.setId(resourceId.getVideoId());
                        videos.setTitle(singleVideo.getSnippet().getTitle());
                        videos.setPublished(new Date(singleVideo.getSnippet().getPublishedAt().getValue()));
                        YouTube.Videos.List youtubeVideos = youTube.videos().list("id,snippet,player,contentDetails,statistics").setId(resourceId.getVideoId());
                        youtubeVideos.setKey(apikey);
                        VideoListResponse videoListResponse = youtubeVideos.execute();

                        if (!videoListResponse.getItems().isEmpty()) {
                            Video video = videoListResponse.getItems().get(0);
                            if (views) {
                                videos.setViewCount(video.getStatistics().getViewCount().longValue());
                            }
                            if (likes) {
                                videos.setLikeCount(video.getStatistics().getLikeCount().longValue());
                            }
                        }
                        videosList.add(videos);
                    }
                }
            }
        }
        return  videosList;
    }
}

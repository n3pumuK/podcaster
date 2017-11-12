package de.exercicse.jrossbach.podcast.search;


import java.util.ArrayList;
import java.util.List;

import de.exercicse.jrossbach.podcast.network.ApiProvider;
import de.exercicse.jrossbach.podcast.network.model.PodcastChannelResponse;
import de.exercicse.jrossbach.podcast.network.model.PodcastItem;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class PodcastChannelDataSource {

    public Single<List<PodcastItemViewModel>> loadChannelItems(String url){
        return ApiProvider.getChannelApi(url).getPodCastChannelResponse().map(new Function<PodcastChannelResponse, List<PodcastItemViewModel>>() {
            @Override
            public List<PodcastItemViewModel> apply(PodcastChannelResponse response) throws Exception {
                List<PodcastItemViewModel> podcastItemViewModelList = new ArrayList<>();
                List<PodcastItem> itemList = response.channel.itemList;
                if(itemList != null){
                    for (PodcastItem item : itemList){
                        PodcastItemViewModel podcastItemViewModel = new PodcastItemViewModel();
                        podcastItemViewModel.setTitle(item.getTitle());
                        podcastItemViewModel.setCategory(item.getCategory());
                        podcastItemViewModel.setPublishingDate(item.getPubDate());
                        podcastItemViewModel.setUrl(item.getEnclosure().getUrl());
                        podcastItemViewModel.setType(item.getEnclosure().getType());
                        podcastItemViewModel.setLength(item.getEnclosure().getLength());
                        String imageUrl = response.channel.normalImage.getNormalImageUrl() != null ? response.channel.normalImage.getNormalImageUrl() : response.channel.channelImage.getItunesImageUrl();
                        podcastItemViewModel.setImageUrl(imageUrl);
                        podcastItemViewModelList.add(podcastItemViewModel);
                    }
                }
                return podcastItemViewModelList;
            }
        });
    }
}

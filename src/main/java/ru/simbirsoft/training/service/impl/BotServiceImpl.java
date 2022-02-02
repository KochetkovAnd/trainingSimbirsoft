package ru.simbirsoft.training.service.impl;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.training.dto.Comment;
import ru.simbirsoft.training.dto.Videos;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.service.BotService;
import ru.simbirsoft.training.utils.SearchVideoYoutube;

import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final RoomServiceImpl roomService;
    private final UserServiceImpl userService;
    private final ConnectionServiceImpl connectionService;
    private final SearchVideoYoutube searchVideoYoutube;

    @Override
    public Object botCommand(String command) {



        if (command.startsWith("//room")) {


            if (command.matches("\\/\\/room create \\{.+\\}")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return roomService.createPublic(roomName);

            } else if (command.matches("\\/\\/room create \\{.+\\} -c")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return roomService.createPrivate(roomName);

            } else if (command.matches("\\/\\/room rename \\{.+\\} \\{.+\\}")) {
                String oldName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                String newName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                return roomService.rename(oldName, newName);

            } else if(command.matches("\\/\\/room remove \\{.+\\}")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return roomService.deleteByName(roomName);

            } else if(command.matches("\\/\\/room connect \\{.+\\} -l \\{.+\\}")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                String userName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                return connectionService.createOther(userName, roomName);

            } else if(command.matches("\\/\\/room disconnect \\{.+\\}")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return connectionService.disconnectSelf(roomName);

            } else if(command.matches("\\/\\/room disconnect \\{.+\\} -l \\{.+\\}")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                String userName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                return connectionService.disconnectOther(userName, roomName, null);

            } else if(command.matches("\\/\\/room disconnect \\{.+\\} -l \\{.+\\} -m \\{.+\\}")) {
                String roomName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                String userName = command.substring(command.indexOf("}" + 1)).substring(command.indexOf("{") + 1, command.indexOf("}"));
                Long time = Long.parseLong(command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}")));
                return connectionService.disconnectOther(userName, roomName, time);

            } else {
                throw new ResourceNotFoundException(command + " command is not exist", "");
            }

        } else if (command.startsWith("//user")) {

            if (command.matches("\\/\\/user rename \\{.+\\} \\{.+\\}")) {
                String oldName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                String newName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                return userService.rename(oldName, newName);

            } else if (command.matches("\\/\\/user ban -l \\{.+\\}")) {
                String userName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return userService.block(userName, null);

            } else if (command.matches("\\/\\/user ban -l \\{.+\\} -m \\{.+\\}")) {
                String userName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                Long time = Long.parseLong(command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}")));
                return userService.block(userName, time);

            } else if (command.matches("\\/\\/user moderator \\{.+\\} -n")) {
                String userName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return userService.makeModer(userName);

            } else if (command.matches("\\/\\/user moderator \\{.+\\} -d")) {
                String userName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return userService.makeModer(userName);

            } else {
                throw new ResourceNotFoundException(command + " command is not exist", "");
            }

        } else if (command.startsWith("//yBot")) {

            if (command.startsWith("//yBot find")) {

                String channelId;
                List<Videos> videosList;
                String channelName;
                String videoName;
                boolean likes;
                boolean views;

                if (command.matches("\\/\\/yBot find \\{.+\\}\\|\\|\\{.+\\}")) {
                    channelName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                    videoName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                    likes = false;
                    views = false;

                } else if (command.matches("\\/\\/yBot find \\{.+\\}\\|\\|\\{.+\\} -v")) {
                    channelName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                    videoName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                    likes = false;
                    views = true;

                } else if (command.matches("\\/\\/yBot find \\{.+\\}\\|\\|\\{.+\\} -v -l")) {
                    channelName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                    videoName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));
                    likes = true;
                    views = true;

                } else {
                    throw new ResourceNotFoundException(command + " command is not exist", "");
                }

                try {
                    if (!channelName.isEmpty()) {
                        channelId = searchVideoYoutube.getChannelId(channelName);
                    } else {
                        channelId = "";
                    }
                    videosList = searchVideoYoutube.getVideoList(videoName, channelId, Long.parseLong("1"), false, likes, views);
                    return videosList;

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            } else if (command.matches("\\/\\/yBot channelInfo \\{.+\\}")) {

                String channelName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                return null;

            } else if (command.matches("\\/\\/yBot videoCommentRandom \\{.+\\}\\|\\|\\{.+\\}")) {

                String channelName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
                String videoName = command.substring(command.lastIndexOf("{") + 1, command.lastIndexOf("}"));

                String channelId;
                List<Videos> videosList;


                try {
                    if (!channelName.isEmpty()) {
                        channelId = searchVideoYoutube.getChannelId(channelName);
                    } else {
                        channelId = "";
                    }
                    videosList = searchVideoYoutube.getVideoList(videoName, channelId, Long.parseLong("1"), false, true, true);
                    Map<String, String> map = searchVideoYoutube.getNameAndCommentMap(videosList.get(0), 100l);
                    Random random = new Random();
                    Comment comment = new Comment();
                    comment.setUserName((String) map.keySet().toArray()[random.nextInt(map.size())]);
                    comment.setText(map.get(comment.getUserName()));
                    return comment;

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                return null;

            } else if (command.matches("\\/\\/yBot help")) {
                String help =
                        "//yBot find {channel_name}||{video_name}\n" +
                                "//yBot find {channel_name}||{video_name} -v\n" +
                                "//yBot find {channel_name}||{video_name} -v -l\n"+
                                "//yBot channelInfo {channel_name}\n"+
                                "//yBot videoCommentRandom {channel_name}||{video_name}";

                return help;

            } else {
                throw new ResourceNotFoundException(command + " command is not exist", "");
            }

        }  else if (command.equals("//help")) {

            String help =
                    "//room create {room_name}\n"+
                            "//room create {room name} -c\n" +
                            "//room remove {room name}\n" +
                            "//room rename {old name} {new name}\n" +
                            "//room connect {room name} -l {user name}\n" +
                            "//room disconnect {room name}\n"+
                            "//room disconnect {room name} -l {user name}\n" +
                            "//room disconnect {room name} -l {user name} -m {time}\n" +
                            "//user rename {old name} {new name}\n" +
                            "//user ban -l {user name}\n" +
                            "//user ban -l {user name} -m {time}\n" +
                            "//user moderator -l {user name} -n\n" +
                            "//user moderator -l {user name} -d\n" +
                            "//yBot find {channel_name}||{video_name}\n" +
                            "//yBot find {channel_name}||{video_name} -v\n" +
                            "//yBot find {channel_name}||{video_name} -v -l\n"+
                            "//yBot channelInfo {channel_name}\n"+
                            "//yBot videoCommentRandom {channel_name}||{video_name}";

            return help;

        }
        throw new ResourceNotFoundException(command + " command is not exist", "");
    }
}

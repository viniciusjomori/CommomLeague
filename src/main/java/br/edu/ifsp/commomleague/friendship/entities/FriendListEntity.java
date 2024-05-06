package br.edu.ifsp.commomleague.friendship.entities;

import java.util.Set;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "friendships")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class FriendListEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToMany()
    @JoinTable(name = "FRIENDLIST_FRIEND",
        joinColumns = @JoinColumn(name = "friendlist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<UserEntity> friends;

    public Set<UserEntity> getFriends() {
        return Set.copyOf(friends);
    }

    public void addFriend(FriendListEntity friend) {
        if(!friend.getUser().getId().equals(this.user.getId()))
            this.friends.add(friend.getUser());
    }

    public void removeFriends(FriendListEntity friendshipEntity) {
        this.friends.remove(friendshipEntity.getUser());
    }

    public boolean isFriend(FriendListEntity friendship) {
        UserEntity user = friendship.getUser();
        return this.friends.contains(user);
    }

}

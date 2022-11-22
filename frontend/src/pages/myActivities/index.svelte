<script lang="ts">
    import { goto } from '@roxi/routify';

    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import EventListElem from '../../lib/Events/EventListElem.svelte';
    import MeetingListElem from '../../lib/Meetings/MeetingListElem.svelte';

    let posts = [];
    let selected: number | null = null;

    const getActivities = () => {
        return execute('users/activities', 'GET')
            .then((r) => r.json())
            .then((r) => (posts = r));
    };

    const clickBubble = (postId) => {
        if (selected === postId) {
            selected = null;
        } else {
            selected = postId;
        }
    };

    let promise = getActivities();
</script>

<div class="h-screen">
    <Header />
    {#await promise then _}
        {#each posts as post}
            {#if Object.hasOwn(post, 'meetingDateTime')}
                <MeetingListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => clickBubble(post.id)} />
            {:else if Object.hasOwn(post, 'startDateTime')}
                <EventListElem data={post} clickHandler={() => $goto(`/events/${post.id}`)} />
            {:else}
                <AnnouncementListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => clickBubble(post.id)} />
            {/if}
        {/each}
    {/await}
</div>

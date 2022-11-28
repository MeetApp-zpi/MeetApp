<script lang="ts">
    import { goto } from '@roxi/routify';

    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import EventListElem from '../../lib/Events/EventListElem.svelte';
    import MeetingListElem from '../../lib/Meetings/MeetingListElem.svelte';

    let posts = [];
    let selected: number | null = null;
    let page: number = 0;

    const retrieveActivities = (page: number) => {
        execute(`users/activities?page=${page}`, 'GET')
            .then((r) => r.json())
            .then((r) => (posts = [...posts, ...r]));
    };

    const clickBubble = (postId) => {
        if (selected === postId) {
            selected = null;
        } else {
            selected = postId;
        }
    };

    $: {
        retrieveActivities(page);
    }

    const infiniteScroll = () => {
        const postsContainer = document.getElementById('postsContainer');

        if (postsContainer.offsetHeight + postsContainer.scrollTop === postsContainer.scrollHeight) {
            page = page + 1;
        }
    };
</script>

<div class="h-screen">
    <Header />
    <div class="" on:scroll={infiniteScroll} id="postsContainer">
        {#each posts as post}
            {#if Object.hasOwn(post, 'meetingDateTime')}
                <MeetingListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => clickBubble(post.id)} />
            {:else if Object.hasOwn(post, 'startDateTime')}
                <EventListElem data={post} clickHandler={() => $goto(`/events/${post.id}`)} />
            {:else}
                <AnnouncementListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => clickBubble(post.id)} />
            {/if}
        {/each}
    </div>
</div>

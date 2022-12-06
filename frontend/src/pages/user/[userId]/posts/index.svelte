<script lang="ts">
    import { redirect } from '@roxi/routify';

    import AnnouncementListElem from '../../../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import EventListElem from '../../../../lib/Events/EventListElem.svelte';
    import Header from '../../../../lib/Header/Header.svelte';
    import MeetingListElem from '../../../../lib/Meetings/MeetingListElem.svelte';

    import execute from '../../../../lib/fetchWrapper';

    export let userId;

    let userPosts = [];
    let selected: number | null = null;
    let page: number = 0;

    const viewDetails = (postId) => {
        if (selected !== postId) {
            selected = postId;
        } else {
            selected = null;
        }
    };

    const retrievePosts = (page: number) => {
        execute(`users/${userId}/posts?page=${page}`, 'GET')
            .then((r) => r.json())
            .then((r) => (userPosts = [...userPosts, ...r]));
    };

    $: {
        retrievePosts(page);
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
    <div class="h-[calc(100%-4rem)] lg:h-[calc(100%-4rem)] lg:w-1/3 lg:mx-auto overflow-auto" on:scroll={infiniteScroll} id="postsContainer">
        {#each userPosts as post}
            {#if Object.hasOwn(post, 'startDateTime')}
                <EventListElem data={post} clickHandler={() => $redirect(`/events/${post.id}`)} />
            {:else if Object.hasOwn(post, 'meetingDateTime')}
                <MeetingListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => viewDetails(post.id)} />
            {:else}
                <AnnouncementListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => viewDetails(post.id)} />
            {/if}
        {/each}
    </div>
</div>

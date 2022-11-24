<script lang="ts">
    import { goto } from '@roxi/routify';

    import AnnouncementListElem from '../../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import EventListElem from '../../../lib/Events/EventListElem.svelte';
    import Header from '../../../lib/Header/Header.svelte';
    import MeetingListElem from '../../../lib/Meetings/MeetingListElem.svelte';
    import PostToolbar from '../../../lib/PostToolbar/PostToolbar.svelte';

    import execute from '../../../lib/fetchWrapper';

    let userPosts = [];
    let selected: number | null = null;

    const viewDetails = (postId) => {
        if (selected !== postId) {
            selected = postId;
        } else {
            selected = null;
        }
    };

    const retrievePostType = (postObj) => {
        if (Object.hasOwn(postObj, 'startDateTime')) {
            return 'events';
        } else if (Object.hasOwn(postObj, 'meetingDateTime')) {
            return 'meetings';
        } else {
            return 'announcements';
        }
    };

    let promise = execute('users/posts', 'GET')
        .then((r) => r.json())
        .then((r) => (userPosts = r));
</script>

<div class="h-screen">
    <Header />
    {#await promise then _}
        {#each userPosts as post}
            {#if retrievePostType(post) === 'events'}
                <EventListElem data={post} clickHandler={() => $goto(`/events/${post.id}`)} />
            {:else if retrievePostType(post) === 'meetings'}
                <MeetingListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => viewDetails(post.id)} />
            {:else}
                <AnnouncementListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => viewDetails(post.id)} />
            {/if}
            <PostToolbar postId={post.id} postType={retrievePostType(post)} />
        {/each}
    {/await}
</div>

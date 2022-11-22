<script lang="ts">
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import EventListElem from '../../lib/Events/EventListElem.svelte';
    import MeetingListElem from '../../lib/Meetings/MeetingListElem.svelte';

    let posts = [];
    let selected: number | null = null;

    let promise = execute('users/activities', 'GET')
        .then((r) => r.json())
        .then((r) => (posts = r));

    $: console.log(posts);
</script>

<div class="h-screen">
    <Header />
    {#await promise then _}
        {#each posts as post}
            {#if Object.hasOwn(post, 'meetingDateTime')}
                <MeetingListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => (selected = post.id)} />
            {:else if Object.hasOwn(post, 'startDateTime')}
                <EventListElem data={post} clickHandler={() => null} />
            {:else}
                <AnnouncementListElem data={post} areDetailsShown={selected === post.id} clickHandler={() => (selected = post.id)} />
            {/if}
        {/each}
    {/await}
</div>

<script lang="ts">
    import { onMount } from 'svelte';

    import AddPostButton from '../../lib/AddPostButton/AddPostButton.svelte';
    import MeetingListElem from '../../lib/Meetings/MeetingListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import SortFilterBanner from '../../lib/SortFilterBanner/SortFilterBanner.svelte';
    import execute from '../../lib/fetchWrapper';
    import { filteredCategoryIds, filteredLocationIds, sortingOption, nameSearchParam, clearFilters } from '../../lib/stores';

    let data = [];
    let selected: number | null = null;
    let sortOptions = [
        { id: 1, name: 'Od najnowszych' },
        { id: 2, name: 'Od najstarszych' },
        { id: 3, name: 'Po liczbie zapisanych rosnąco' },
        { id: 4, name: 'Po liczbie zapisanych malejąco' },
        { id: 5, name: 'Najbliżej daty rozpoczęcia' }
    ];
    let meetingsPromise: Promise<any>;
    let page: number = 0;

    clearFilters();

    const viewDetails = (postId) => {
        if (selected !== postId) {
            selected = postId;
        } else {
            selected = null;
        }
    };

    const infiniteScroll = () => {
        const meetingsContainer = document.getElementById('postsContainer');

        if (meetingsContainer.offsetHeight + meetingsContainer.scrollTop === meetingsContainer.scrollHeight) {
            page = page + 1;
        }
    };

    const retrieveMeetings = (page: number, urlParams: URLSearchParams) => {
        execute(`meetings?page=${page}&` + urlParams.toString(), 'GET')
            .then((r) => r.json())
            .then((r) => (data = r));
    };

    $: {
        let urlParams = new URLSearchParams();
        for (let categoryId of $filteredCategoryIds) {
            urlParams.append('categoryIds', categoryId);
        }
        for (let locationId of $filteredLocationIds) {
            urlParams.append('locationIds', locationId);
        }
        if ($sortingOption !== null) {
            urlParams.append('sortOption', $sortingOption.toString());
        }
        if ($nameSearchParam !== null) {
            urlParams.append('nameSearch', $nameSearchParam);
        }

        retrieveMeetings(page, urlParams);
    }

    onMount(() => {
        window.addEventListener('scroll', infiniteScroll);
    });
</script>

<div class="h-screen">
    <Header />
    <SortFilterBanner {sortOptions} />
    <div class="h-[calc(100%-10rem)] lg:h-[calc(100%-14rem)] overflow-auto" on:scroll={infiniteScroll} id="postsContainer">
        {#await meetingsPromise then _}
            {#each data as item}
                <MeetingListElem areDetailsShown={selected === item.id} data={item} clickHandler={() => viewDetails(item.id)} />
            {/each}
        {/await}
    </div>
    <AddPostButton pageType="meetings" />
    <Footer pageType="meetings" />
</div>

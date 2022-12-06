<script lang="ts">
    import { goto } from '@roxi/routify';

    import AddPostButton from '../../lib/AddPostButton/AddPostButton.svelte';
    import EventListElem from '../../lib/Events/EventListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import SortFilterBanner from '../../lib/SortFilterBanner/SortFilterBanner.svelte';
    import SortFilterColumn from '../../lib/SortFilterColumn/SortFilterColumn.svelte';
    import execute from '../../lib/fetchWrapper';
    import { filteredCategoryIds, filteredLocationIds, sortingOption, nameSearchParam, clearFilters } from '../../lib/stores';
    import ShowActiveButton from '../../lib/ShowActiveButton/ShowActiveButton.svelte';

    let data = [];
    let sortOptions = [
        { id: 1, name: 'Od najnowszych' },
        { id: 2, name: 'Od najstarszych' },
        { id: 3, name: 'Po liczbie zapisanych rosnąco' },
        { id: 4, name: 'Po liczbie zapisanych malejąco' },
        { id: 5, name: 'Najbliżej daty rozpoczęcia' }
    ];
    let page: number = 0;

    clearFilters();

    const infiniteScroll = () => {
        const eventsContainer = document.getElementById('postsContainer');

        if (eventsContainer.offsetHeight + eventsContainer.scrollTop === eventsContainer.scrollHeight) {
            page = page + 1;
        }
    };

    const viewDetails = (postId) => {
        $goto(`/events/${postId}`);
    };

    const retrieveEvents = (page: number, urlParams: URLSearchParams) => {
        execute(`eventsInactive?page=${page}&` + urlParams.toString(), 'GET')
            .then((r) => r.json())
            .then((r) => (data = [...data, ...r]));
    };

    $: {
        $filteredCategoryIds;
        $filteredLocationIds;
        $sortingOption;
        $nameSearchParam;
        data = [];
        page = 0;
    }

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

        retrieveEvents(page, urlParams);
    }
</script>

<div class="h-screen">
    <Header />
    <SortFilterBanner {sortOptions} />
    <div class="h-[calc(100%-10rem)] lg:h-[calc(100%-4rem)]  lg:flex lg:flex-row" on:scroll={infiniteScroll} id="postsContainer">
        <div class="hidden lg:block lg:w-1/3 lg:max-w-[33.3333%] lg:bg-green-mist overflow-auto">
            <SortFilterColumn {sortOptions} />
        </div>
        <div class="flex flex-col h-full lg:w-full overflow-auto">
            {#each data as item}
                <EventListElem data={item} clickHandler={() => viewDetails(item.id)} />
            {/each}
        </div>
        <div class="hidden lg:block lg:w-1/3" />
    </div>
    <ShowActiveButton />
    <Footer pageType="events" />
</div>

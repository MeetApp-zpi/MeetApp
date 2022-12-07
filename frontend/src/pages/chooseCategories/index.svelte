<script lang="ts">
    import Button from '../../lib/Button/Button.svelte';
    import SelectablePill from '../../lib/Pill/SelectablePill.svelte';
    import execute from '../../lib/fetchWrapper';
    import { userDetails } from '../../lib/stores';
    import { redirect } from '@roxi/routify';

    let currentUser = null;
    let categories = null;
    let userExistingInterests = null;
    let selectedCategories = [];

    execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));

    execute('users/categories', 'GET')
        .then((r) => r.json())
        .then((r) => {
            for (const interest of r) {
                selectedCategories.push(interest.id);
            }
        });

    $: console.log(selectedCategories);

    execute('users/details', 'GET')
        .then((r) => r.json())
        .then((r) => (currentUser = r));

    const clickedCategory = (categoryId: number) => {
        if (selectedCategories.includes(categoryId)) {
            selectedCategories = selectedCategories.filter((i) => i !== categoryId);
        } else {
            selectedCategories = [...selectedCategories, categoryId];
        }
    };

    const submitChanges = () => {
        execute('users/categories', 'PUT', {
            categories: selectedCategories
        }).then((r) => (window.location.href = 'http://localhost:5173'));
    };

    const waitForUser = async () => {
        const result = await execute('users/details', 'GET');
        const data = await result.json();

        if (data === null) {
            $redirect('/login');
        }
    };
</script>

{#await waitForUser() then _}
    <div class="flex flex-col bg-ivory h-screen justify-between lg:w-1/3 lg:mx-auto lg:py-10">
        <div class="flex flex-col items-center">
            {#if currentUser !== null}
                <div class="text-cocoa text-2xl py-4 uppercase">Cześć {currentUser.firstName}!</div>
            {/if}
            <div class="text-cocoa px-8 text-base">
                <p>Wybranie zainteresowań pozwoli nam na dopasowanie treści specjalnie dla Ciebie.</p>
            </div>
            <div class="px-2 py-2">
                {#if categories !== null}
                    {#each categories as category}
                        <SelectablePill
                            class="mx-2 my-1 px-6"
                            isSelected={selectedCategories.includes(category.id)}
                            clickCallback={() => clickedCategory(category.id)}>{category.name}</SelectablePill
                        >
                    {/each}
                {/if}
            </div>
        </div>
        <div class="flex flex-col items-center">
            <div class="">
                <Button clickHandler={submitChanges} class="px-10 py-1">Gotowe!</Button>
            </div>
            <button on:click={() => (window.location.href = 'http://localhost:5173/')}>
                <div class="text-sm text-sage px-16 text-center py-2">Wybiorę zainteresowania następnym razem</div>
            </button>
        </div>
    </div>
{/await}

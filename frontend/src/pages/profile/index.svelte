<script lang="ts">
    import { redirect, url } from '@roxi/routify';
    import { fade, fly } from 'svelte/transition';

    import execute from '../../lib/fetchWrapper';
    import { userDetails } from '../../lib/stores';

    import Pill from '../../lib/Pill/Pill.svelte';
    import Header from '../../lib/Header/Header.svelte';

    import FaCommentAlt from 'svelte-icons/fa/FaCommentAlt.svelte';
    import FaHandsHelping from 'svelte-icons/fa/FaHandsHelping.svelte';
    import MdDeleteForever from 'svelte-icons/md/MdDeleteForever.svelte';
    import MdEdit from 'svelte-icons/md/MdEdit.svelte';

    let isDeleteModalOpen: boolean = false;

    async function fetchUserInterests() {
        return await execute('users/categories', 'GET').then(async (response) => {
            if (!response.ok) {
                $redirect('/login');
            }

            return await response.json();
        });
    }

    const deleteAccount = () => {
        execute('users', 'DELETE').then((r) => (r.status === 200 ? (window.location.href = window.location.href) : console.log(r)));
    };

    const hideDeleteModal = () => {
        isDeleteModalOpen = false;
    };

    const showDeleteModal = () => {
        isDeleteModalOpen = true;
    };
</script>

<div class="h-screen bg-ivory">
    <Header />
    {#await fetchUserInterests() then userInterests}
        <div class="flex flex-col items-center px-6 overflow-scroll h-[calc(100%-4rem)]">
            <div class="w-64 h-64 my-2 flex-none">
                <img class="rounded-full w-64 h-64" src={$userDetails.profilePicture} alt="Profile avatar" />
            </div>

            <div class="text-cocoa text-3xl font-bold pb-12 text-center">{$userDetails.firstName} {$userDetails.lastName}</div>

            <div class="flex flex-col items-center pb-12">
                <div class="flex flex-row items-center mb-5">
                    <div class="h-6 w-6 mr-4" />
                    <p class="text-cocoa text-xl font-bold text-center">Moje Zainteresowania</p>
                    <a href={$url('/chooseCategories')} class="h-6 w-6 ml-4 hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">
                        <MdEdit />
                    </a>
                </div>

                <div class="flex flex-wrap justify-center">
                    {#each userInterests as interest, index}
                        <Pill class="flex self-center px-4 mx-1 my-1">
                            {interest.name}
                        </Pill>
                    {/each}
                </div>
            </div>

            <a class="mb-4 flex flex-row items-center" href={$url('/user/posts')}>
                <div class="h-5 w-7 mr-2 text-cocoa">
                    <FaCommentAlt />
                </div>
                <p class="text-pine text-2xl hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">Moje Posty</p>
            </a>
            <a class="mb-4 flex flex-row items-center" href={$url('/myActivities')}>
                <div class="h-6 w-6 mr-2 text-cocoa">
                    <FaHandsHelping />
                </div>
                <p class="text-pine text-2xl hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">Biorę udział</p>
            </a>
            <button class="mb-8 flex flex-row items-center" on:click={showDeleteModal}>
                <div class="h-6 w-6 mr-2 text-cocoa">
                    <MdDeleteForever />
                </div>
                <p class="text-pine text-2xl hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">Usuń konto</p>
            </button>
        </div>
    {/await}
    {#if isDeleteModalOpen}
        <div
            class="z-20 flex flex-col fixed h-64 bg-ivory w-48 rounded-xl border-pine border-2 left-1/2 -ml-24 top-1/2 -mt-32 items-center justify-around"
            transition:fly
        >
            <div class="text-center text-cocoa font-bold">Czy na pewno chcesz usunąć swoje konto?</div>
            <button
                on:click={deleteAccount}
                class="bg-pickle border-pine border-2 text-ivory
            rounded-full hover:opacity-90 transition ease-in-out delay-100
            focus:ring focus:ring-tea font-bold px-10 py-1">TAK</button
            >
            <button
                on:click={hideDeleteModal}
                class="bg-orange border-taupe border-2 text-ivory
            rounded-full hover:opacity-90 transition ease-in-out delay-100
            focus:ring focus:ring-tea font-bold px-10 py-1">NIE</button
            >
        </div>
        <div
            class="z-10 w-screen h-screen fixed bg-black bg-opacity-30 top-0 left-0"
            transition:fade
            on:click={hideDeleteModal}
            on:keydown={hideDeleteModal}
        />
    {/if}
</div>

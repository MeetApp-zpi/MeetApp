<script lang="ts">
    import execute from '../../lib/fetchWrapper';
    import {redirect, url} from '@roxi/routify';
    import Header from '../../lib/Header/Header.svelte';
    import Pill from '../../lib/Pill/Pill.svelte';
    import {userDetails} from '../../lib/stores';
    import MdEdit from 'svelte-icons/md/MdEdit.svelte';

    async function fetchUserInterests() {
        return await execute('users/categories', 'GET').then(async (response) => {
            if (!response.ok) {
                $redirect('/login');
            }

            return await response.json();
        });
    }
</script>

<div class="h-screen bg-ivory">
    <Header/>
    {#await fetchUserInterests() then userInterests}
        <div class="flex flex-col items-center px-8 overflow-scroll h-[calc(100%-4rem)]">
            <div class="w-64 h-64 mr-2">
                <img class="rounded-full" src={$userDetails.profilePicture} alt="Profile avatar"/>
            </div>

            <div class="text-cocoa text-3xl font-bold pb-12 text-center">{$userDetails.firstName} {$userDetails.lastName}</div>

            <div class="flex flex-col items-center pb-12">
                <div class="flex flex-row">
                    <div class="h-6 w-6 mr-4"></div>
                    <p class="text-cocoa text-xl font-bold pb-5 text-center">Moje Zainteresowania</p>
                    <a href={$url('/chooseCategories')}
                       class="h-6 w-6 ml-4 hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">
                        <MdEdit/>
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

            <a class="mb-4" href={$url('/posts')}>
                <p class="text-pine text-2xl hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">Moje
                    Posty</p>
            </a>
            <a class="mb-8" href={$url('/myActivities')}>
                <p class="text-pine text-2xl hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">Zapisane
                    Posty</p>
            </a>
        </div>
    {/await}
</div>
